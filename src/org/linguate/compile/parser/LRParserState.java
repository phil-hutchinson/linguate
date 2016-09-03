/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author Phil Hutchinson
 */
public interface LRParserState
{
    String getName();
    LRParserAction getAction(GrammarTerminal nextSymbol);
    LRParserAction getEndOfInputAction();
    LRParserState getPostReductionState(GrammarNonTerminal productionBase);
}
