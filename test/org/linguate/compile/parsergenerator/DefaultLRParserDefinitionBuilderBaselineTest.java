/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parsergenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.parser.LRParserDefinition;
import org.linguate.compile.parser.LRParserDefinition.ActionType;
import org.linguate.compile.parsergenerator.DefaultLRParserDefinitionBuilderWrappers.*;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultLRParserDefinitionBuilderBaselineTest {
    
    DefaultLRParserDefinitionBuilder instance;
    GrammarTerminal terminal;
    GrammarTerminal terminal2;
    GrammarNonTerminal nonTerminal;
    GrammarNonTerminal nonTerminal2;
    GrammarProduction production;
    GrammarProduction production2;
    
    public DefaultLRParserDefinitionBuilderBaselineTest() {
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new DefaultLRParserDefinitionBuilder();
        terminal = new GrammarTerminalWrapper("SampleTerminal");
        terminal2 = new GrammarTerminalWrapper("SampleTerminal2");
        nonTerminal = new GrammarNonTerminalWrapper("SampleNonTerminal");
        nonTerminal2 = new GrammarNonTerminalWrapper("SampleNonTerminal2");
        production = new GrammarProductionWrapper(nonTerminal);
        production2 = new GrammarProductionWrapper(nonTerminal2);
    }
    
    @After
    public void tearDown() {
    }

    //int addState()
    @Test
    public void addState_firstCall_startState() {
        int expected = org.linguate.compile.parser.LRParserDefinition.START_STATE;
        int actual = instance.addState();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void addState_afterGetDefinition_throws() {
        instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.addState();
    }
    
    //void addShift(int fromState, GrammarTerminal nextSymbol, int toState)
    @Test
    public void addShift_0invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;

        expectedException.expect(IllegalArgumentException.class);
        instance.addShift(invalidState, terminal, startState);
    }

    @Test
    public void addShift_2invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;
        
        expectedException.expect(IllegalArgumentException.class);
        instance.addShift(startState, terminal, invalidState);
    }

    @Test
    public void addShift_02valid1nonnull_correctOutput() {
        int startState = instance.addState();
        int secondState = instance.addState();
        instance.addShift(startState, terminal, secondState);
        LRParserDefinition definition = instance.getDefinition();
        
        ActionType expectedAction = ActionType.Shift;
        ActionType actualAction = definition.getActionType(startState, terminal);
        assertEquals(expectedAction, actualAction);
        
        int expected = secondState;
        int actual = definition.getShiftState(startState, terminal);
        assertEquals(expected, actual);
    }

    @Test
    public void addShift_1null_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        
        expectedException.expect(NullPointerException.class);
        instance.addShift(startState, null, secondState);
    }

    @Test
    public void addShift_01repeated_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        int thirdState = instance.addState();
        instance.addShift(startState, terminal, secondState);
        
        expectedException.expect(IllegalStateException.class);
        instance.addShift(startState, terminal, thirdState);
    }
    
    @Test
    public void addShift_01sameAsReduction_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        instance.addReduction(startState, terminal, production);
        
        expectedException.expect(IllegalStateException.class);
        instance.addShift(startState, terminal, secondState);
    }
    
    @Test
    public void addShift_afterGetDefinition_throws() {
        int state0 = instance.addState();
        int state1 = instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.addShift(state0, terminal, state1);
    }
    
    //void addReduction(int state, GrammarTerminal nextSymbol, GrammarProduction production)
    @Test
    public void addReduction_0invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;

        expectedException.expect(IllegalArgumentException.class);
        instance.addReduction(invalidState, terminal, production);
    }

    @Test
    public void addReduction_02valid1nonnull_correctOutput() {
        int startState = instance.addState();
        instance.addReduction(startState, terminal, production);
        LRParserDefinition definition = instance.getDefinition();
        
        ActionType expectedAction = ActionType.Reduce;
        ActionType actualAction = definition.getActionType(startState, terminal);
        assertEquals(expectedAction, actualAction);
        
        GrammarProduction expected = production;
        GrammarProduction actual = definition.getReduceRule(startState, terminal);
        assertEquals(expected, actual);
    }

    @Test
    public void addReduction_02valid1null_correctOutput() {
        int startState = instance.addState();
        instance.addReduction(startState, null, production);
        LRParserDefinition definition = instance.getDefinition();
        
        ActionType expectedAction = ActionType.Reduce;
        ActionType actualAction = definition.getActionType(startState, null);
        assertEquals(expectedAction, actualAction);
        
        GrammarProduction expected = production;
        GrammarProduction actual = definition.getReduceRule(startState, null);
        assertEquals(expected, actual);
    }

    @Test
    public void addReduction_01repeated_throws() {
        int startState = instance.addState();
        instance.addReduction(startState, terminal, production);
        
        expectedException.expect(IllegalStateException.class);
        instance.addReduction(startState, terminal, production2);
    }

    @Test
    public void addReduction_01sameAsShift_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        instance.addShift(startState, terminal, secondState);
        
        expectedException.expect(IllegalStateException.class);
        instance.addReduction(startState, terminal, production);
    }
    
    @Test
    public void addReduction_0sameAsAccept1null_throws() {
        int startState = instance.addState();
        instance.addAccept(startState);
        
        expectedException.expect(IllegalStateException.class);
        instance.addReduction(startState, null, production);
    }
    
    @Test
    public void addReduction_afterGetDefinition_throws() {
        int state0 = instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.addReduction(state0, terminal, production);
    }
    
    // void addAccept(int state)
    @Test
    public void addAccept_0invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;

        expectedException.expect(IllegalArgumentException.class);
        instance.addAccept(invalidState);
    }

    @Test
    public void addAccept_0valid_correctOutput() {
        int startState = instance.addState();
        instance.addAccept(startState);
        LRParserDefinition definition = instance.getDefinition();
        
        ActionType expectedAction = ActionType.Accept;
        ActionType actualAction = definition.getActionType(startState, null);
        assertEquals(expectedAction, actualAction);
    }

    @Test
    public void addAccept_0repeated_throws() {
        int startState = instance.addState();
        instance.addAccept(startState);
        
        expectedException.expect(IllegalStateException.class);
        instance.addAccept(startState);
    }

    @Test
    public void addAccept_0sameAsReduce0null1_throws() {
        int startState = instance.addState();
        instance.addReduction(startState, null, production);
        
        expectedException.expect(IllegalStateException.class);
        instance.addAccept(startState);
    }
    
    @Test
    public void addAccept_afterGetDefinition_throws() {
        int state0 = instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.addAccept(state0);
    }
    
    // void addPostReductionState(int stackState, GrammarNonTerminal productionHead, int newState)
    @Test
    public void addPostReductionState_0invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;

        expectedException.expect(IllegalArgumentException.class);
        instance.addPostReductionState(invalidState, nonTerminal, startState);
    }
    
    @Test
    public void addPostReductionState_1null_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        
        expectedException.expect(NullPointerException.class);
        instance.addPostReductionState(startState, null, secondState);
    }
    
    @Test
    public void addPostReductionState_2invalid_throws() {
        int startState = instance.addState();
        int invalidState = startState + 1;

        expectedException.expect(IllegalArgumentException.class);
        instance.addPostReductionState(startState, nonTerminal, invalidState);
    }
    
    @Test
    public void addPostReductionState_02valid1nonnull_correctOutput() {
        int startState = instance.addState();
        int secondState = instance.addState();
        instance.addPostReductionState(startState, nonTerminal, secondState);
        LRParserDefinition definition = instance.getDefinition();
        
        int expected = secondState;
        int actual = definition.getPostReductionState(startState, nonTerminal);
        assertEquals(expected, actual);
    }
    
    @Test
    public void addPostReductionState_01repeated_throws() {
        int startState = instance.addState();
        int secondState = instance.addState();
        int thirdState = instance.addState();
        instance.addPostReductionState(startState, nonTerminal, secondState);
        
        expectedException.expect(IllegalStateException.class);
        instance.addPostReductionState(startState, nonTerminal, thirdState);
    }

    @Test
    public void addPostReductionState_afterGetDefinition_throws() {
        int state0 = instance.addState();
        int state1 = instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.addPostReductionState(state0, nonTerminal, state1);
    }
    
    //LRParserDefinition getDefinition()
    @Test
    public void getDefinition_noAddState_throws() {
        expectedException.expect(IllegalStateException.class);
        instance.getDefinition();
    }
    
    @Test
    public void getDefinition_repeated_throws() {
        instance.addState();
        instance.getDefinition();
        
        expectedException.expect(IllegalStateException.class);
        instance.getDefinition();
    }
    
    @Test
    public void getDefinition_valid_correctOutput() {
        int state0 = instance.addState();
        int state1 = instance.addState();
        int state2 = instance.addState();
        
        instance.addShift(state0, terminal, state1);
        instance.addShift(state0, terminal2, state2);
        instance.addReduction(state1, terminal, production);
        instance.addReduction(state2, terminal, production2);
        instance.addReduction(state1, null, production2);
        instance.addAccept(state2);
        instance.addPostReductionState(state1, nonTerminal, state2);
        instance.addPostReductionState(state1, nonTerminal2, state0);
        instance.addPostReductionState(state2, nonTerminal, state1);
        
        LRParserDefinition definition = instance.getDefinition();
        
        assertEquals(LRParserDefinition.ActionType.Reject, definition.getActionType(state0, null));
        assertEquals(LRParserDefinition.ActionType.Shift, definition.getActionType(state0, terminal));
        assertEquals(LRParserDefinition.ActionType.Shift, definition.getActionType(state0, terminal2));
        assertEquals(LRParserDefinition.ActionType.Reduce, definition.getActionType(state1, null));
        assertEquals(LRParserDefinition.ActionType.Reduce, definition.getActionType(state1, terminal));
        assertEquals(LRParserDefinition.ActionType.Reject, definition.getActionType(state1, terminal2));
        assertEquals(LRParserDefinition.ActionType.Accept, definition.getActionType(state2, null));
        assertEquals(LRParserDefinition.ActionType.Reduce, definition.getActionType(state2, terminal));
        assertEquals(LRParserDefinition.ActionType.Reject, definition.getActionType(state2, terminal2));
        
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state0, null));
        assertEquals(state1, definition.getShiftState(state0, terminal));
        assertEquals(state2, definition.getShiftState(state0, terminal2));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state1, null));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state1, terminal));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state1, terminal2));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state2, null));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state2, terminal));
        assertEquals(LRParserDefinition.NO_SHIFT_STATE, definition.getShiftState(state2, terminal2));

        assertEquals(null, definition.getReduceRule(state0, null));
        assertEquals(null, definition.getReduceRule(state0, terminal));
        assertEquals(null, definition.getReduceRule(state0, terminal2));
        assertEquals(production2, definition.getReduceRule(state1, null));
        assertEquals(production, definition.getReduceRule(state1, terminal));
        assertEquals(null, definition.getReduceRule(state1, terminal2));
        assertEquals(null, definition.getReduceRule(state2, null));
        assertEquals(production2, definition.getReduceRule(state2, terminal));
        assertEquals(null, definition.getReduceRule(state2, terminal2));
        
        assertEquals(LRParserDefinition.NO_POST_REDUCTION_STATE, definition.getPostReductionState(state0, nonTerminal));
        assertEquals(LRParserDefinition.NO_POST_REDUCTION_STATE, definition.getPostReductionState(state0, nonTerminal2));
        assertEquals(state2, definition.getPostReductionState(state1, nonTerminal));
        assertEquals(state0, definition.getPostReductionState(state1, nonTerminal2));
        assertEquals(state1, definition.getPostReductionState(state2, nonTerminal));
        assertEquals(LRParserDefinition.NO_POST_REDUCTION_STATE, definition.getPostReductionState(state2, nonTerminal2));
    }
}
