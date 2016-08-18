/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.compile;

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
