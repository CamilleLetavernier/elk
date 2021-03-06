/*******************************************************************************
 * Copyright (c) 2013, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.core.ui;

import java.util.EnumSet;

import org.eclipse.elk.core.options.LayoutOptions;
import org.eclipse.elk.core.service.LayoutMetaDataService;
import org.eclipse.elk.core.service.data.LayoutAlgorithmData;
import org.eclipse.elk.core.service.data.LayoutOptionData;
import org.eclipse.elk.core.service.data.LayoutTypeData;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A label provider for values of layout options. An instance of this label provider must be
 * associated with a specific option in order to correctly translate the passed values.
 *
 * @author msp
 * @kieler.design proposed by msp
 * @kieler.rating proposed yellow by msp
 */
public class LayoutOptionLabelProvider extends LabelProvider {
    
    /** the layout option data instance associated with this label provider. */
    private final LayoutOptionData optionData;

    /**
     * Create a label provider for the given layout option.
     * 
     * @param optionData a layout option
     */
    public LayoutOptionLabelProvider(final LayoutOptionData optionData) {
        this.optionData = optionData;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(final Object element) {
        ElkUiPlugin.Images images = ElkUiPlugin.getDefault().getImages();
        switch (optionData.getType()) {
        case OBJECT:
        case STRING:
            return images.getPropText();
        case BOOLEAN:
            boolean istrue = true;
            if (element instanceof Boolean) {
                istrue = (Boolean) element;
            } else if (element instanceof Integer) {
                istrue = (Integer) element == 1;
            }
            if (istrue) {
                return images.getPropTrue();
            } else {
                return images.getPropFalse();
            }
        case ENUM:
        case ENUMSET:
            return images.getPropChoice();
        case INT:
            return images.getPropInt();
        case FLOAT:
            return images.getPropFloat();
        default:
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public String getText(final Object element) {
        switch (optionData.getType()) {
        case STRING:
            if (LayoutOptions.ALGORITHM.equals(optionData)) {
                LayoutMetaDataService layoutDataService = LayoutMetaDataService.getInstance();
                LayoutAlgorithmData algorithmData = layoutDataService.getAlgorithmData((String) element);
                if (algorithmData != null) {
                    String categoryName = layoutDataService.getCategoryName(algorithmData.getCategory());
                    if (categoryName == null) {
                        return algorithmData.getName();
                    } else {
                        return algorithmData.getName() + " (" + categoryName + ")";
                    }
                }
                return Messages.getString("kiml.ui.8");
            }
            break;
        case BOOLEAN:
            if (element instanceof Boolean) {
                return ((Boolean) element).toString();
            }
            // fall through so the same method as for enums is applied
        case ENUM:
            if (element instanceof Integer) {
                return optionData.getChoices()[(Integer) element];
            }
            break;
        case ENUMSET:
            if (element instanceof String) {
                return (String) element;
            } else if (element instanceof String[]) {
                String[] arr = (String[]) element;
                if (arr.length == 0) {
                    return "";
                } else {
                    StringBuilder builder = new StringBuilder();
                    
                    for (String s : arr) {
                        builder.append(", ").append(s);
                    }
                    
                    return builder.substring(2);
                }
            } else if (element instanceof EnumSet) {
                EnumSet set = (EnumSet) element;
                if (set.isEmpty()) {
                    return "";
                }
                
                StringBuilder builder = new StringBuilder();
                for (Object o : set) {
                    builder.append(", " + ((Enum) o).name());
                }
                return builder.substring(2);
            }
        }
        return element.toString();
    }
    
}
