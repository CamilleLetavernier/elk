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
package org.eclipse.elk.graphviz.dot.serializer;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;

/**
 * Customized syntactic sequencer to enforce serialization of some optional keywords.
 * 
 * @author msp
 * @kieler.design proposed by msp
 * @kieler.rating proposed yellow by msp
 */
@SuppressWarnings("restriction")
public class GraphvizDotSyntacticSequencer extends AbstractGraphvizDotSyntacticSequencer {
    
    // CHECKSTYLEOFF MethodName
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void emit_AttributeStatement_CommaKeyword_2_1_0_q(final EObject semanticObject,
            final ISynNavigable transition, final List<INode> nodes) {
        acceptUnassignedKeyword(grammarAccess.getAttributeStatementAccess().getCommaKeyword_2_1_0(),
                ",", null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void emit_EdgeStatement_CommaKeyword_2_1_1_0_q(final EObject semanticObject,
            final ISynNavigable transition, final List<INode> nodes) {
        acceptUnassignedKeyword(grammarAccess.getEdgeStatementAccess().getCommaKeyword_2_1_1_0(),
                ",", null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void emit_NodeStatement_CommaKeyword_1_1_1_0_q(final EObject semanticObject,
            final ISynNavigable transition, final List<INode> nodes) {
        acceptUnassignedKeyword(grammarAccess.getNodeStatementAccess().getCommaKeyword_1_1_1_0(),
                ",", null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void emit_Statement_SemicolonKeyword_1_q(final EObject semanticObject,
            final ISynNavigable transition, final List<INode> nodes) {
        acceptUnassignedKeyword(grammarAccess.getStatementAccess().getSemicolonKeyword_1(),
                ";", null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void emit_Subgraph_SubgraphKeyword_1_0_q(final EObject semanticObject,
            final ISynNavigable transition, final List<INode> nodes) {
        acceptUnassignedKeyword(grammarAccess.getSubgraphAccess().getSubgraphKeyword_1_0(),
                "subgraph", null);
    }
    
}
