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
package org.eclipse.elk.graphviz.layouter.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.elk.graphviz.layouter.GraphvizLayoutProvider;
import org.eclipse.elk.graphviz.layouter.GraphvizTool;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values for the GraphViz layouter
 * plug-in.
 * 
 * @author ars
 * @kieler.design proposed by msp
 * @kieler.rating proposed yellow by msp
 */
public class GraphvizLayouterPreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = GraphvizLayouterPreferenceStore.getInstance().getPreferenceStore();

        store.setDefault(GraphvizTool.PREF_TIMEOUT, GraphvizTool.PROCESS_DEF_TIMEOUT);
        store.setDefault(GraphvizLayoutProvider.PREF_GRAPHVIZ_REUSE_PROCESS,
                GraphvizLayoutProvider.REUSE_PROCESS_DEFAULT);
    }
}
