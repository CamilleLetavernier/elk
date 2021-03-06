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
package org.eclipse.elk.layered.intermediate.greedyswitch;

import org.eclipse.elk.layered.graph.LNode;
import org.eclipse.elk.layered.intermediate.greedyswitch.SwitchDecider.CrossingCountSide;
import org.eclipse.elk.layered.properties.GreedySwitchType;

/**
 * This class manages the crossing matrix and fills it on demand. It needs to be reinitialized for
 * each free layer. For each layer the node.id fields MUST be set from 0 to layer.getSize() - 1!
 * 
 * @author alan
 */
public class CrossingMatrixFiller {
    private final boolean[][] isCrossingMatrixFilled;
    private final int[][] crossingMatrix;
    private final BetweenLayerEdgeTwoNodeCrossingsCounter inBetweenLayerCrossingCounter;
    private final CrossingCountSide direction;
    private final boolean oneSided;

    // SUPPRESS CHECKSTYLE NEXT 30 Javadoc
    /**
     * Constructs class which manages the crossing matrix.
     */
    public CrossingMatrixFiller(final GreedySwitchType greedyType, final LNode[][] graph,
            final int freeLayerIndex, final CrossingCountSide direction) {
        
        this.direction = direction;
        oneSided = greedyType.isOneSided();

        LNode[] freeLayer = graph[freeLayerIndex];
        isCrossingMatrixFilled = new boolean[freeLayer.length][freeLayer.length];
        crossingMatrix = new int[freeLayer.length][freeLayer.length];

        inBetweenLayerCrossingCounter =
                new BetweenLayerEdgeTwoNodeCrossingsCounter(graph, freeLayerIndex);
    }

    /**
     * Returns entry for crossings between edges incident to two nodes, where upperNode is above
     * lowerNode in the layer.
     */
    public int getCrossingMatrixEntry(final LNode upperNode, final LNode lowerNode) {
        if (!isCrossingMatrixFilled[upperNode.id][lowerNode.id]) {
            fillCrossingMatrix(upperNode, lowerNode);
            isCrossingMatrixFilled[upperNode.id][lowerNode.id] = true;
            isCrossingMatrixFilled[lowerNode.id][upperNode.id] = true;
        }
        return crossingMatrix[upperNode.id][lowerNode.id];
    }

    private void fillCrossingMatrix(final LNode upperNode, final LNode lowerNode) {
        if (oneSided) {
            switch (direction) {
            case EAST:
                inBetweenLayerCrossingCounter.countEasternEdgeCrossings(upperNode, lowerNode);
                break;
            case WEST:
                inBetweenLayerCrossingCounter.countWesternEdgeCrossings(upperNode, lowerNode);
            }
        } else {
            inBetweenLayerCrossingCounter.countBothSideCrossings(upperNode, lowerNode);
        }
        crossingMatrix[upperNode.id][lowerNode.id] =
                inBetweenLayerCrossingCounter.getUpperLowerCrossings();
        crossingMatrix[lowerNode.id][upperNode.id] =
                inBetweenLayerCrossingCounter.getLowerUpperCrossings();
    }

}
