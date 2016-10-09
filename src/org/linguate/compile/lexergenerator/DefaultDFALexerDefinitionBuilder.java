/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexer.DFALexerDefinition;

/**
 *
 * @author phil
 */
public class DefaultDFALexerDefinitionBuilder implements DFALexerDefinitionBuilder {
    private InternalDFALexerDefinition definition;
    
    public DefaultDFALexerDefinitionBuilder() {
        definition = new InternalDFALexerDefinition();
    }
    @Override
    public void addTransition(int fromState, char inputSymbol, int toState) {
        Edge edge = new Edge(fromState, inputSymbol);
        definition.transitions.put(edge, toState);
    }

    @Override
    public void addAccept(int state, GrammarTerminal accepts) {
        definition.acceptStates.put(state, accepts);
    }

    @Override
    public DFALexerDefinition getDefinition() {
        return definition;
    }
    
    private static class Edge {
        public final int startState;
        public final char inputSymbol;

        public Edge(int startState, char inputSymbol) {
            this.startState = startState;
            this.inputSymbol = inputSymbol;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 2819 * hash + this.startState;
            hash = 2819 * hash + this.inputSymbol;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Edge other = (Edge) obj;
            if (this.startState != other.startState) {
                return false;
            }
            if (this.inputSymbol != other.inputSymbol) {
                return false;
            }
            return true;
        }
    }
    
    private static class InternalDFALexerDefinition implements DFALexerDefinition {
        private Map<Integer, GrammarTerminal> acceptStates = new HashMap<>();
        private Map<Edge, Integer> transitions = new HashMap<>();
        
        @Override
        public int getNextState(int currentState, char character) {
            Edge edge = new Edge(currentState, character);
            if (transitions.containsKey(edge)) {
                return transitions.get(edge);
            } else {
                return DEAD_STATE;
            }
        }

        @Override
        public int getCharacterCongruency(char character) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getNextState(int currentState, int characterCongruency) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public GrammarTerminal accepts(int state) {
            if (acceptStates.containsKey(state)) {
                return acceptStates.get(state);
            } else {
                return null;
            }
        }

        @Override
        public Set<GrammarTerminal> getAllAcceptTerminals() {
            return new HashSet<GrammarTerminal>(acceptStates.values());
        }
    }
}
