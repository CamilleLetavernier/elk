/*******************************************************************************
 * Copyright (c) 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.graphviz.layouter.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.elk.graphviz.layouter.GraphvizLayouterPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * In order to avoid extending {@link org.eclipse.ui.plugin.AbstractUIPlugin AbstractUIPlugin} by
 * the {@link GraphvizLayouterPreferenceStore} plugin, we maintain our own preference store here.
 * Usually the preference store is accessed using
 * {@link org.eclipse.ui.plugin.AbstractUIPlugin#getPreferenceStore()
 * AbstractUIPlugin#getPreferenceStore()}. The initialization of the store here is taken from the
 * just mentioned ui plugin.
 * 
 * @author uru
 */
public final class GraphvizLayouterPreferenceStore {

    private IPreferenceStore preferenceStore;

    private static GraphvizLayouterPreferenceStore instance;

    private GraphvizLayouterPreferenceStore() {
    }

    /**
     * @return an instance of {@link OgdfPreferenceStore}.
     */
    public static GraphvizLayouterPreferenceStore getInstance() {
        if (instance == null) {
            instance = new GraphvizLayouterPreferenceStore();
        }
        return instance;
    }

    /**
     * Functionally equal to {@link org.eclipse.ui.plugin.AbstractUIPlugin#getPreferenceStore()}.
     * 
     * @return the preference store
     */
    @SuppressWarnings("deprecation")
    public IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
            preferenceStore =
                    new ScopedPreferenceStore(new InstanceScope(), GraphvizLayouterPlugin
                            .getDefault().getBundle().getSymbolicName());
        }
        return preferenceStore;
    }
}
