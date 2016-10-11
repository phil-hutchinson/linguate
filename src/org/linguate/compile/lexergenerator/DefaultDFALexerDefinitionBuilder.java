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
    public void addCharactersToCongruency(int congruencyNumber, Iterable<Character> chars) {
        chars.forEach(ch -> {
            if (definition.characterCongruencies.containsKey(ch)) {
                if (definition.characterCongruencies.get(ch) != congruencyNumber) {
                    throw new IllegalArgumentException("Character: " + ch + "has already been assigned to a different character congruency.");
                }
            } else {
                definition.characterCongruencies.put(ch, congruencyNumber);
            }
        });
    }
    
    @Override
    public void addTransition(int fromState, int congruencyNumber, int toState) {
        Edge edge = new Edge(fromState, congruencyNumber);
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
        public final int congruencyNumber;

        public Edge(int startState, int congruencyNumber) {
            this.startState = startState;
            this.congruencyNumber = congruencyNumber;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 2819 * hash + this.startState;
            hash = 2819 * hash + this.congruencyNumber;
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
            if (this.congruencyNumber != other.congruencyNumber) {
                return false;
            }
            return true;
        }
    }
    
    private static class InternalDFALexerDefinition implements DFALexerDefinition {
        private Map<Character, Integer> characterCongruencies = new HashMap<>();
        private Map<Integer, GrammarTerminal> acceptStates = new HashMap<>();
        private Map<Edge, Integer> transitions = new HashMap<>();
        
        @Override
        public int getNextState(int currentState, char character) {
            if (characterCongruencies.containsKey(character)) {
                int congruency = characterCongruencies.get(character);
                Edge edge = new Edge(currentState, congruency);
                if (transitions.containsKey(edge)) {
                    return transitions.get(edge);
                } else {
                    return DEAD_STATE;
                }
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
