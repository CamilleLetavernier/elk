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
package org.eclipse.elk.layered.graph;

import java.util.Collections;
import java.util.List;

import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.layered.properties.InternalProperties;
import org.eclipse.elk.layered.properties.PortType;
import org.eclipse.elk.layered.properties.Properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A node in a layered graph.
 *
 * @author msp
 * @kieler.design proposed by msp
 * @kieler.rating yellow 2013-03-22 review KI-35 by chsch, grh
 */
public final class LNode extends LShape {
    
    /**
     * Definition of node types used in the layered approach.
     * 
     * @author msp
     * @author cds
     * @author ima
     * @author jjc
     * @kieler.design proposed by msp
     * @kieler.rating proposed yellow by msp
     */
    public static enum NodeType {
        
        /** a normal node is created from a node of the original graph. */
        NORMAL,
        /** a dummy node created to split a long edge. */
        LONG_EDGE,
        /** a node representing an external port. */
        EXTERNAL_PORT,
        /** a dummy node created to cope with ports at the northern or southern side. */
        NORTH_SOUTH_PORT,
        /** a dummy node to represent a mid-label on an edge. */
        LABEL,
        /** a dummy node originating from a node spanning multiple layers. */
        BIG_NODE;
        
        /**
         * Return the color used when writing debug output graphs. The colors are given as strings of
         * the form "#RGB", where each component is given as a two-digit hexadecimal value.
         * 
         * @return the color string
         */
        public String getColor() {
            switch (this) {
            case BIG_NODE: return "#cccccc";
            case EXTERNAL_PORT: return "#cc99cc";
            case LONG_EDGE: return "#eaed00";
            case NORTH_SOUTH_PORT: return "#0034de";
            case LABEL: return "#75c3c3";
            default: return "#000000";
            }
        }

    }
    
    /** the serial version UID. */
    private static final long serialVersionUID = -4272570519129722541L;
    
    /** the containing graph. */
    private LGraph graph;
    /** the containing layer. */
    private Layer layer;
    /** the node's node type. */
    private NodeType type = NodeType.NORMAL;
    /** the ports of the node. */
    private final List<LPort> ports = Lists.newArrayListWithCapacity(6);
    /** this node's labels. */
    private final List<LLabel> labels = Lists.newArrayListWithCapacity(2);
    /** the margin area around this node. */
    private final LInsets margin = new LInsets();
    /** the insets inside this node, usually reserved for port and label placement. */
    private final LInsets insets = new LInsets();
    
    /**
     * Creates a node.
     * 
     * @param graph the graph for which the node is created 
     */
    public LNode(final LGraph graph) {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "n_" + getDesignation();
    }
    
    /**
     * Returns the name of the node. The name is derived from the text of the first label, if any.
     * 
     * @return the name, or {@code null}
     */
    public String getName() {
        if (!labels.isEmpty()) {
            return labels.get(0).getText();
        }
        return null;
    }
    
    /**
     * Returns the node's designation. The designation is either the name if that is not {@code null},
     * or the node's ID otherwise.
     * 
     * @return the node's designation.
     */
    public String getDesignation() {
        String name = getName();
        if (name == null) {
            return Integer.toString(id);
        } else {
            return name;
        }
    }

    /**
     * Returns the layer that owns this node.
     * 
     * @return the owning layer
     */
    public Layer getLayer() {
        return layer;
    }

    /**
     * Sets the owning layer and adds itself to the end of the layer's list of nodes. If the node
     * was previously in another layer, it is removed from that layer's list of nodes. Be careful
     * not to use this method while iterating through the nodes list of the old layer nor of the new
     * layer, since that could lead to {@link java.util.ConcurrentModificationException}s.
     * 
     * @param thelayer
     *            the owner to set
     */
    public void setLayer(final Layer thelayer) {
        if (this.layer != null) {
            this.layer.getNodes().remove(this);
        }
        
        this.layer = thelayer;
        
        if (this.layer != null) {
            this.layer.getNodes().add(this);
        }
    }
    
    /**
     * Returns the graph that contains this node.
     * 
     * @return the containing graph
     */
    public LGraph getGraph() {
        if (graph == null && layer != null) {
            return layer.getGraph();
        }
        return graph;
    }
    
    /**
     * Set the containing graph. This method must not be used when the layer has already been
     * assigned. The graphs' lists of nodes are <em>not</em> automatically updated.
     * 
     * @param newGraph
     *            the new containing graph
     */
    public void setGraph(final LGraph newGraph) {
        assert layer == null;
        this.graph = newGraph;
    }
    
    /**
     * Sets the containing layer and adds itself to the layer's list of nodes at the specified
     * position. If the node was previously in another layer, it is removed from that layer's list
     * of nodes. Be careful not to use this method while iterating through the nodes list of the old
     * layer nor of the new layer, since that could lead to
     * {@link java.util.ConcurrentModificationException}s.
     * 
     * @param index
     *            where the node should be inserted in the layer. Must be {@code >= 0} and
     *            {@code <= layer.getNodes().size()}.
     * @param newlayer
     *            the new layer
     */
    public void setLayer(final int index, final Layer newlayer) {
        if (newlayer != null && (index < 0 || index > newlayer.getNodes().size())) {
            throw new IllegalArgumentException("index must be >= 0 and <= layer node count");
        }
        
        if (this.layer != null) {
            this.layer.getNodes().remove(this);
        }
        
        this.layer = newlayer;
        
        if (newlayer != null) {
            newlayer.getNodes().add(index, this);
        }
    }
    
    /**
     * Returns the node's node type. Parts of the algorithm will treat nodes of different types
     * differently.
     * 
     * @return the node's node type.
     */
    public NodeType getType() {
        return type;
    }
    
    /**
     * Sets this node's node type.
     * 
     * @param type
     *            the node's new node type.
     */
    public void setType(final NodeType type) {
        this.type = type;
    }

    /**
     * Returns the list of ports of this node. Note that all edges are connected to specific ports,
     * even if the original diagram does not have any ports. Before the crossing minimization phase
     * has passed, the port order in this list is arbitrary. After crossing minimization the order
     * of ports corresponds to the clockwise order in which they are drawn, starting with the north
     * side. Hence the order is
     * <ul>
     *   <li>north ports from left to right,</li>
     *   <li>east ports from top to bottom,</li>
     *   <li>south ports from right to left,</li>
     *   <li>west port from bottom to top.</li>
     * </ul>
     * 
     * @return the ports of this node
     */
    public List<LPort> getPorts() {
        return ports;
    }
    
    /**
     * Returns an iterable for all ports of given type.
     * 
     * @param portType
     *            a port type
     * @return an iterable for the ports of given type
     */
    public Iterable<LPort> getPorts(final PortType portType) {
        switch (portType) {
        case INPUT:
            return Iterables.filter(ports, LPort.INPUT_PREDICATE);
        case OUTPUT:
            return Iterables.filter(ports, LPort.OUTPUT_PREDICATE);
        default:
            return Collections.emptyList();
        }
    }
    
    /**
     * Returns an iterable for all ports of given side.
     * 
     * @param side a port side
     * @return an iterable for the ports of given side
     */
    public Iterable<LPort> getPorts(final PortSide side) {
        switch (side) {
        case NORTH:
            return Iterables.filter(ports, LPort.NORTH_PREDICATE);
        case EAST:
            return Iterables.filter(ports, LPort.EAST_PREDICATE);
        case SOUTH:
            return Iterables.filter(ports, LPort.SOUTH_PREDICATE);
        case WEST:
            return Iterables.filter(ports, LPort.WEST_PREDICATE);
        default:
            return Collections.emptyList();
        }
    }
    
    /**
     * Returns an iterable for all ports of a given type and side.
     * 
     * @param portType a port type.
     * @param side a port side.
     * @return an iterable for the ports of the given type and side.
     */
    public Iterable<LPort> getPorts(final PortType portType, final PortSide side) {
        Predicate<LPort> typePredicate = null;
        switch (portType) {
        case INPUT:
            typePredicate = LPort.INPUT_PREDICATE;
            break;
        case OUTPUT:
            typePredicate = LPort.OUTPUT_PREDICATE;
            break;
        }
        
        Predicate<LPort> sidePredicate = null;
        switch (side) {
        case NORTH:
            sidePredicate = LPort.NORTH_PREDICATE;
            break;
        case EAST:
            sidePredicate = LPort.EAST_PREDICATE;
            break;
        case SOUTH:
            sidePredicate = LPort.SOUTH_PREDICATE;
            break;
        case WEST:
            sidePredicate = LPort.WEST_PREDICATE;
            break;
        }
        
        if (typePredicate != null && sidePredicate != null) {
            return Iterables.filter(ports, Predicates.and(typePredicate, sidePredicate));
        } else {
            return Collections.emptyList();
        }
    }
    
    /**
     * Returns an iterable for all inomcing edges.
     * 
     * @return an iterable for all incoming edges.
     */
    public Iterable<LEdge> getIncomingEdges() {
        List<Iterable<LEdge>> iterables = Lists.newArrayList();
        for (LPort port : ports) {
            iterables.add(port.getIncomingEdges());
        }
        
        return Iterables.concat(iterables);
    }
    
    /**
     * Returns an iterable for all outgoing edges.
     * 
     * @return an iterable for all outgoing edges.
     */
    public Iterable<LEdge> getOutgoingEdges() {
        List<Iterable<LEdge>> iterables = Lists.newArrayList();
        for (LPort port : ports) {
            iterables.add(port.getOutgoingEdges());
        }
        
        return Iterables.concat(iterables);
    }
    
    /**
     * Returns an iterable for all connected edges, both incoming and outgoing.
     * 
     * @return an iterable for all connected edges.
     */
    public Iterable<LEdge> getConnectedEdges() {
        List<Iterable<LEdge>> iterables = Lists.newArrayList();
        for (LPort port : ports) {
            iterables.add(port.getConnectedEdges());
        }
        
        return Iterables.concat(iterables);
    }
    
    /**
     * Returns this node's labels.
     * 
     * @return this node's labels.
     */
    public List<LLabel> getLabels() {
        return labels;
    }
    
    /**
     * Returns the node's margin. The margin is the space around the node that is to be reserved
     * for ports and labels.
     * 
     * <p>The margin is not automatically updated. Rather, the margin has to be calculated once
     * the port and label positions are fixed. Usually this is right before the node placement
     * starts.</p>
     *  
     * @return the node's margin. May be modified.
     */
    public LInsets getMargin() {
        return margin;
    }
    
    /**
     * Returns the node's insets. The insets describe the area inside the node that is used by
     * ports, port labels, and node labels.
     * 
     * <p>The insets are not automatically updated. Rather, the insets have to be calculated
     * once the port and label positions are fixed. Usually this is right before node placement
     * starts.</p>
     * 
     * @return the node's insets. May be modified.
     */
    public LInsets getInsets() {
        return insets;
    }
    
    /**
     * Returns the index of the node in the containing layer's list of nodes.
     * Note that this method has linear running time in the number of nodes,
     * so use it with caution.
     * 
     * @return the index of this node, or -1 if the node has no owner
     */
    public int getIndex() {
        if (layer == null) {
            return -1;
        } else {
            return layer.getNodes().indexOf(this);
        }
    }
    
    /**
     * Converts the position of this node from coordinates relative to the parent node's border to
     * coordinates relative to that node's content area. The content area is the parent node border
     * minus insets minus border spacing minus offset.
     * 
     * @param horizontal
     *            if {@code true}, the x coordinate will be translated.
     * @param vertical
     *            if {@code true}, the y coordinate will be translated.
     * @throws IllegalStateException
     *             if the node is not assigned to a layer in a layered graph.
     */
    public void borderToContentAreaCoordinates(final boolean horizontal, final boolean vertical) {
        LGraph thegraph = getGraph();
        
        LInsets graphInsets = thegraph.getInsets();
        float borderSpacing = thegraph.getProperty(InternalProperties.BORDER_SPACING);
        KVector offset = thegraph.getOffset();
        KVector pos = getPosition();
        
        if (horizontal) {
            pos.x = pos.x - graphInsets.left - borderSpacing - offset.x;
        }
        
        if (vertical) {
            pos.y = pos.y - graphInsets.top - borderSpacing - offset.y;
        }
    }

    /**
     * Returns the position of this node's interactive reference point. This position depends on the
     * graph's {@link Properties#INTERACTIVE_REFERENCE_POINT} property. It determines on which
     * basis node positions are compared with each other in interactive layout phases.
     * 
     * @return the node's anchor point position.
     */
    public KVector getInteractiveReferencePoint() {
        switch (getGraph().getProperty(Properties.INTERACTIVE_REFERENCE_POINT)) {
        case CENTER:
            KVector nodePos = getPosition();
            KVector nodeSize = getSize();
            
            return new KVector(nodePos.x + nodeSize.x / 2.0, nodePos.y + nodeSize.y / 2.0);
        
        case TOP_LEFT:
            return new KVector(getPosition());
        
        default:
            // This shouldn't happen.
            return null;
        }
    }

}
