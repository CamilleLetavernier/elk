/*******************************************************************************
 * Copyright (c) 2011, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.graphviz.dot.transform;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A graph format handler for Graphviz Dot. Instantiate through a proper injector:
 * 
 * <code>
 * new GraphvizDotStandaloneSetup()
 *     .createInjectorAndDoEMFRegistration()
 *     .getInstance(DotResourceSetProvider.class);
 * </code>
 *
 * @author cds
 */
public final class DotResourceSetProvider {
    
    @Inject private Provider<XtextResourceSet> resourceSetProvider;
    
    /**
     * Returns a new resource set for dot files.
     * 
     * @return resource set.
     */
    public ResourceSet createResourceSet() {
        return resourceSetProvider.get();
    }
    
}
