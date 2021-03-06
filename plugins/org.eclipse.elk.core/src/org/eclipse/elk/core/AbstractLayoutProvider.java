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
 * <p>When used in Eclipse, layout providers must be registered through the {@code layoutProviders}
 * extension point, which is defined by the {@code org.eclipse.elk.core.service} plugin.</p>
 * 
 * @kieler.design 2011-01-17 reviewed by haf, cmot, soh
 * @kieler.rating yellow 2012-08-10 review KI-23 by cds, sgu
 * @author ars
 * @author msp
 */
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
