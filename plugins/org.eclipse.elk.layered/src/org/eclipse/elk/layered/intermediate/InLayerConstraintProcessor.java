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
package org.eclipse.elk.layered.intermediate;

import java.util.List;

import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.layered.ILayoutProcessor;
import org.eclipse.elk.layered.graph.LGraph;
import org.eclipse.elk.layered.graph.LNode;
import org.eclipse.elk.layered.graph.Layer;
import org.eclipse.elk.layered.properties.InLayerConstraint;
import org.eclipse.elk.layered.properties.InternalProperties;

import com.google.common.collect.Lists;

/**
 * Makes sure that in-layer constraints are respected. This processor is only necessary
 * if a crossing minimizer doesn't support in-layer constraints anyway. Crossing minimizers
 * that do shouldn't include a dependency on this processor. It would need time without
 * actually doing anything worthwhile.
 * 
 * <p>Please note that, among top- and bottom-placed nodes, in-layer successor constraints
 * are not respected by this processor. It does, however, preserve them if the crossing
 * reduction phase did respect them.</p>
 * 
 * <dl>
 *   <dt>Precondition:</dt><dd>a layered graph; crossing minimization is already finished.</dd>
 *   <dt>Postcondition:</dt><dd>nodes may have been reordered to match in-layer constraints.</dd>
 *   <dt>Slots:</dt><dd>Before phase 4.</dd>
 *   <dt>Same-slot dependencies:</dt><dd>None.</dd>
 * </dl>
 * 
 * @author cds
 * @kieler.design 2012-08-10 chsch grh
 * @kieler.rating proposed yellow by msp
 */
public final class InLayerConstraintProcessor implements ILayoutProcessor {

    /**
     * {@inheritDoc}
     */
    public void process(final LGraph layeredGraph, final IElkProgressMonitor monitor) {
        monitor.begin("Layer constraint edge reversal", 1);
        
        // Iterate through each layer
        for (Layer layer : layeredGraph) {
            /* We'll go through the layer's nodes, remembering two things:
             *  1. Once we reach the first non-top-constrained node, we remember its
             *     index. Top-constrained nodes encountered afterwards must be inserted
             *     at that point.
             *  2. A list of bottom-constrained nodes we have encountered. They will
             *     afterwards be moved to the end of the list, keeping the order in
             *     which we've encountered them.
             */
            
            int topInsertionIndex = -1;
            List<LNode> bottomConstrainedNodes = Lists.newArrayList();
            
            // Iterate through an array of its nodes
            LNode[] nodes = layer.getNodes().toArray(new LNode[layer.getNodes().size()]);
            
            for (int i = 0; i < nodes.length; i++) {
                InLayerConstraint constraint =
                    nodes[i].getProperty(InternalProperties.IN_LAYER_CONSTRAINT);
                
                if (topInsertionIndex == -1) {
                    // See if this node is the first non-top-constrained node
                    if (constraint != InLayerConstraint.TOP) {
                        topInsertionIndex = i;
                    }
                } else {
                    // We have already encountered non-top-constrained nodes before
                    if (constraint == InLayerConstraint.TOP) {
                        // Move the node to the top insertion point
                        nodes[i].setLayer(null);
                        nodes[i].setLayer(topInsertionIndex++, layer);
                    }
                }
                
                // Put BOTTOM-constrained nodes into the corresponding list
                if (constraint == InLayerConstraint.BOTTOM) {
                    bottomConstrainedNodes.add(nodes[i]);
                }
            }
            
            // Append the bottom-constrained nodes
            for (LNode node : bottomConstrainedNodes) {
                node.setLayer(null);
                node.setLayer(layer);
            }
        }
        
        monitor.done();
    }

}
