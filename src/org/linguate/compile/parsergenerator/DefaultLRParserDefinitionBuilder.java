/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parsergenerator;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.parser.LRParserDefinition;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultLRParserDefinitionBuilder implements LRParserDefinitionBuilder {

    @Override
    public int addState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addShift(int fromState, GrammarTerminal nextSymbol, int toState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addReduction(int state, GrammarTerminal nextSymbol, GrammarProduction production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addAccept(int state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void addPostReductionState(int stackState, GrammarNonTerminal productionHead, int newState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LRParserDefinition getDefinition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
