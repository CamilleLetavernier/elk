/*******************************************************************************
 * Copyright (c) 2008, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.core;

/**
 * A layout provider executes a layout algorithm to layout the child elements of a node.
 * <p>When used in Eclipse, layout providers must register through the {@code layoutProviders}
 * extension point. All layout providers published to Eclipse this way are collected in the
 * {@link LayoutMetaDataService} singleton, provided the UI plugin is loaded.
 * 
 * @kieler.design 2011-01-17 reviewed by haf, cmot, soh
 * @kieler.rating yellow 2012-08-10 review KI-23 by cds, sgu
 * @author ars
 * @author msp
 */
// cds: Update the documentation.
// cds: Also, I understand why layout providers are also layout engines now. However, this should be
//      somehow documented since the documentation of IGraphLayoutEngine is hardly applicable to
//      the raw layout algorithm.
public abstract class AbstractLayoutProvider implements IGraphLayoutEngine {

    /**
     * Initialize the layout provider with the given parameter.
     * 
     * @param parameter a string used to parameterize the layout provider instance
     */
    public void initialize(final String parameter) {
        // do nothing - override in subclasses
    }
    
    /**
     * Dispose the layout provider by releasing any resources that are held.
     */
    public void dispose() {
        // do nothing - override in subclasses
    }

}
