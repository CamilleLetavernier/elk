/*******************************************************************************
 * Copyright (c) 2010, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.core.options;

/**
 * Definition of the edge types. To be accessed using {@link LayoutOptions#EDGE_TYPE}.
 * 
 * @kieler.design 2011-03-14 reviewed by cmot, cds
 * @kieler.rating yellow 2013-01-09 review KI-32 by ckru, chsch
 * @author mri
 */
public enum EdgeType {
    
    /** no special type. */
    NONE,
    /** the edge is directed. */
    DIRECTED,
    /** the edge is undirected. */
    UNDIRECTED,
    /** the edge represents an association. */
    ASSOCIATION,
    /** the edge represents a generalization. */
    GENERALIZATION,
    /** the edge represents a dependency. */
    DEPENDENCY;
    
}
