/*******************************************************************************
 * Copyright (c) 2009, 2015 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Kiel University - initial API and implementation
 *******************************************************************************/
package org.eclipse.elk.graph.properties;

/**
 * Interface for property identifiers. Properties have a type and a default value, and
 * they have an internal mechanism for identification, which should be compatible
 * with their {@link java.lang.Object#equals(Object)} and {@link java.lang.Object#hashCode()}
 * implementations.
 *
 * @kieler.design 2011-01-17 reviewed by haf, cmot, soh
 * @kieler.rating proposed yellow 2012-07-10 msp
 * @param <T> type of the property
 * @author msp
 */
public interface IProperty<T> {
    
    /**
     * Returns the default value of this property.
     * 
     * @return the default value, or {@code null} if the property has no default value
     */
    T getDefault();
    
    /**
     * Returns the lower bound of this property. If there is no lower bound, a
     * comparable is returned that is smaller than everything else.
     * 
     * @return the lower bound
     */
    @Deprecated
    // cds: Why are these deprecated? Don't we still want the ability to check whether layout option
    //      values are within a valid range?
    // msp: The lower/upper bounds were introduced for the evolutionary layout, for which I completely
    //      dropped the support. Maybe we could reintroduce bounds when we design a DSL for layout options,
    //      but an important question then is what to do with those bounds. If a value exceeds a given
    //      bound, should we throw an exception or change the value to match the bound? I think an optimal
    //      solution would be an early feedback such as error markers directly in the corresponding row
    //      of the Layout view.
    Comparable<? super T> getLowerBound();
    
    /**
     * Returns the upper bound of this property. If there is no upper bound, a
     * comparable is returned that is greater than everything else.
     * 
     * @return the upper bound
     */
    @Deprecated
    Comparable<? super T> getUpperBound();
    
    /**
     * Returns an identifier string for this property.
     * 
     * @return an identifier
     */
    String getId();

}
