/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parsergenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.parser.LRParserDefinition;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultLRParserDefinitionBuilder implements LRParserDefinitionBuilder {
    private static Accepter accepter = new Accepter();
    private int nextState = LRParserDefinition.START_STATE;
    private boolean definitionGenerated = false;
    InternalLRParserDefinition definition = new InternalLRParserDefinition();
    
    @Override
    public int addState() {
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, no further changes possible.");
        }
        int result = nextState;
        nextState++;
        return result;
    }

    @Override
    public void addShift(int fromState, GrammarTerminal nextSymbol, int toState) {
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, no further changes possible.");
        }
        
        if (fromState < LRParserDefinition.START_STATE || fromState >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        if (toState < LRParserDefinition.START_STATE || toState >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        if (nextSymbol == null) {
            throw new NullPointerException("nextSymbol cannot be null.");
        }
        
        ActionInputs actionInputs = new ActionInputs(fromState, nextSymbol);
        
        if (definition.actions.containsKey(actionInputs)) {
            throw new IllegalStateException("Action already defined for (state,terminal) pair.");
        }
        
        definition.actions.put(actionInputs, toState);
    }

    @Override
    public void addReduction(int state, GrammarTerminal nextSymbol, GrammarProduction production) {
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, no further changes possible.");
        }
        
        if (state < LRParserDefinition.START_STATE || state >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        ActionInputs actionInputs = new ActionInputs(state, nextSymbol);
        
        if (definition.actions.containsKey(actionInputs)) {
            throw new IllegalStateException("Action already defined for (state,terminal) pair.");
        }
        
        definition.actions.put(actionInputs, production);
    }

    @Override
    public void addAccept(int state) {
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, no further changes possible.");
        }
        
        if (state < LRParserDefinition.START_STATE || state >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        ActionInputs actionInputs = new ActionInputs(state, null);
        
        if (definition.actions.containsKey(actionInputs)) {
            throw new IllegalStateException("Action already defined for (state,terminal) pair.");
        }
        
        definition.actions.put(actionInputs, accepter);
    }
    
    @Override
    public void addPostReductionState(int stackState, GrammarNonTerminal productionHead, int newState) {
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, no further changes possible.");
        }
        
        if (stackState < LRParserDefinition.START_STATE || stackState >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        if (newState < LRParserDefinition.START_STATE || newState >= nextState) {
            throw new IllegalArgumentException("States must be created using addState().");
        }
        
        if (productionHead == null) {
            throw new NullPointerException("productionHead cannot be null.");
        }
        
        PostReductionInputs postReductionInputs = new PostReductionInputs(stackState, productionHead);
        
        if (definition.postReductionStates.containsKey(postReductionInputs)) {
            throw new IllegalStateException("Post reduction state already defined for (state,nonterminal) pair.");
        }
        
        definition.postReductionStates.put(postReductionInputs, newState);
    }

    @Override
    public LRParserDefinition getDefinition() {
        if (nextState == LRParserDefinition.START_STATE) {
            throw new IllegalStateException("Require at least one state to generate definition.");
        }
        if (definitionGenerated) {
            throw new IllegalStateException("Definition already generated, it cannot be generated a second time.");
        }
        definitionGenerated = true;
        return definition;
    }

    private static class ActionInputs {
        private int state;
        private GrammarTerminal terminal;

        public ActionInputs(int state, GrammarTerminal terminal) {
            this.state = state;
            this.terminal = terminal;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 47 * hash + this.state;
            hash = 47 * hash + Objects.hashCode(this.terminal);
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
            final ActionInputs other = (ActionInputs) obj;
            if (this.state != other.state) {
                return false;
            }
            if (!Objects.equals(this.terminal, other.terminal)) {
                return false;
            }
            return true;
        }
        
    }
    
    private static class PostReductionInputs {
        private int state;
        private GrammarNonTerminal nonTerminal;

        public PostReductionInputs(int state, GrammarNonTerminal nonTerminal) {
            this.state = state;
            this.nonTerminal = nonTerminal;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + this.state;
            hash = 47 * hash + Objects.hashCode(this.nonTerminal);
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
            final PostReductionInputs other = (PostReductionInputs) obj;
            if (this.state != other.state) {
                return false;
            }
            if (!Objects.equals(this.nonTerminal, other.nonTerminal)) {
                return false;
            }
            return true;
        }

    }
    
    private static class Accepter{ }
    
    private static class InternalLRParserDefinition implements LRParserDefinition {
        Map<ActionInputs, Object> actions = new HashMap<>();
        Map<PostReductionInputs, Integer> postReductionStates = new HashMap<>();
        
        @Override
        public ActionType getActionType(int state, GrammarTerminal nextSymbol) {
            ActionInputs actionInputs = new ActionInputs(state, nextSymbol);
            
            if (actions.containsKey(actionInputs)) {
                Object value = actions.get(actionInputs);
                
                if (value instanceof Integer) {
                    return ActionType.Shift;
                } else if (value instanceof GrammarProduction) {
                    return ActionType.Reduce;
                } else if (value instanceof Accepter) {
                    return ActionType.Accept;
                }
                
            }
            
            return ActionType.Reject;
        }

        @Override
        public int getShiftState(int state, GrammarTerminal nextSymbol) {
            ActionInputs actionInputs = new ActionInputs(state, nextSymbol);
            
            if (actions.containsKey(actionInputs)) {
                Object value = actions.get(actionInputs);
                
                if (value instanceof Integer) {
                    return (Integer)value;
                } 
                
            }
            
            return LRParserDefinition.NO_SHIFT_STATE;
        }

        @Override
        public GrammarProduction getReduceRule(int state, GrammarTerminal nextSymbol) {
            ActionInputs actionInputs = new ActionInputs(state, nextSymbol);
            
            if (actions.containsKey(actionInputs)) {
                Object value = actions.get(actionInputs);
                
                if (value instanceof GrammarProduction) {
                    return (GrammarProduction)value;
                } 
                
            }
            
            return null;
        }

        @Override
        public int getPostReductionState(int state, GrammarNonTerminal productionHead) {
            PostReductionInputs postReductionInputs = new PostReductionInputs(state, productionHead);

            if (postReductionStates.containsKey(postReductionInputs)) {
                return postReductionStates.get(postReductionInputs);
            }
            else
            {
                return LRParserDefinition.NO_POST_REDUCTION_STATE;
            }
        }
        
    }
}
