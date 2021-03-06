/*
* generated by Xtext
*/
package org.eclipse.elk.graphviz.dot.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.elk.graphviz.dot.services.GraphvizDotGrammarAccess;

public class GraphvizDotParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private GraphvizDotGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_PREC_LINE");
	}
	
	@Override
	protected org.eclipse.elk.graphviz.dot.parser.antlr.internal.InternalGraphvizDotParser createParser(XtextTokenStream stream) {
		return new org.eclipse.elk.graphviz.dot.parser.antlr.internal.InternalGraphvizDotParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "GraphvizModel";
	}
	
	public GraphvizDotGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(GraphvizDotGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
