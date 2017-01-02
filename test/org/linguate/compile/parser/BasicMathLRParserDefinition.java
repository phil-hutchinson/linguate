/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import java.util.HashMap;
import java.util.Map;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathLRParserDefinition implements LRParserDefinition
{
    private int nextState = 0;
    private Map<Integer, Map<GrammarTerminal, Action>> actions = new HashMap<>();
    private Map<Integer, Map<GrammarNonTerminal, Integer>> postReductionStates = new HashMap<>();
    
    public BasicMathLRParserDefinition()
    {
    }
    
    public int addState() {
        int result = nextState;
        nextState++;
        return result;
    }
    
    public void addShift(int fromState, GrammarTerminal nextSymbol, int toState) {
        Action shiftAction = Action.CreateShift(toState);
        addAction(fromState, nextSymbol, shiftAction);
    }
    
    public void addReduce(int fromState, GrammarTerminal nextSymbol, GrammarProduction production) {
        Action reduceAction = Action.CreateReduce(production);
        addAction(fromState, nextSymbol, reduceAction);
    }
    
    public void addAccept(int fromState, GrammarTerminal nextSymbol) {
        Action acceptAction = Action.CreateAccept();
        addAction(fromState, nextSymbol, acceptAction);
    }
    
    public void addPostReductionState(int fromState, GrammarNonTerminal productionHead, int toState) {
        Map<GrammarNonTerminal, Integer> innerMap;
        if (postReductionStates.containsKey(fromState)) {
            innerMap = postReductionStates.get(fromState);
            if (innerMap.containsKey(productionHead)) {
                throw new IllegalStateException("Post reduction already exists.");
            }
        } else {
            innerMap = new HashMap<>();
            postReductionStates.put(fromState, innerMap);
        }
        
        innerMap.put(productionHead, toState);
    }
    
    @Override
    public ActionType getActionType(int state, GrammarTerminal nextSymbol) {
        Action action = getAction(state, nextSymbol);
        if (action == null) {
            return ActionType.Reject;
        } else {
            return action.getType();
        }
    }

    @Override
    public int getShiftState(int state, GrammarTerminal nextSymbol) {
        Action action = getAction(state, nextSymbol);
        if (action == null) {
            throw new IllegalStateException();
        } else {
            return action.getShiftState();
        }
    }

    @Override
    public GrammarProduction getReduceRule(int state, GrammarTerminal nextSymbol) {
        Action action = getAction(state, nextSymbol);
        if (action == null) {
            throw new IllegalStateException();
        } else {
            return action.getReduceProduction();
        }
    }

    @Override
    public int getPostReductionState(int state, GrammarNonTerminal productionHead) {
        if (postReductionStates.containsKey(state)) {
            Map<GrammarNonTerminal, Integer> innerMap = postReductionStates.get(state);
            if (innerMap.containsKey(productionHead)) {
                return innerMap.get(productionHead);
            }
        }
        
        return NO_POST_REDUCTION_STATE;
    }

    private void addAction(int state, GrammarTerminal terminal, Action action) {
        Map<GrammarTerminal, Action> innerMap;
        if (actions.containsKey(state)) {
            innerMap = actions.get(state);
            if (innerMap.containsKey(terminal)) {
                throw new IllegalStateException("Action already exists.");
            }
        } else {
            innerMap = new HashMap<>();
            actions.put(state, innerMap);
        }
        
        innerMap.put(terminal, action);
    }
    
    private Action getAction(int state, GrammarTerminal terminal) {
        if (actions.containsKey(state)) {
            Map<GrammarTerminal, Action> innerMap = actions.get(state);
            if (innerMap.containsKey(terminal)) {
                return innerMap.get(terminal);
            }
        }
        
        return null;
    }
    
    private static class Action {
        private ActionType type;
        private int newState;
        private GrammarProduction production;
        
        private Action(ActionType type) {
            this.type = type;
        }
        
        public static Action CreateAccept() {
            return new Action(ActionType.Accept);
        }
        
        public static Action CreateShift(int newState) {
            Action result = new Action(ActionType.Shift);
            result.newState = newState;
            return result;
        }
        
        public static Action CreateReduce(GrammarProduction production) {
            Action result = new Action(ActionType.Reduce);
            result.production = production;
            return result;
        }

        public ActionType getType() {
            return type;
        }

        public int getShiftState() {
            if (type != ActionType.Shift) {
                throw new IllegalStateException();
            }
            return newState;
        }

        public GrammarProduction getReduceProduction() {
            if (type != ActionType.Reduce) {
                throw new IllegalStateException();
            }
            return production;
        }
        
    }
}
