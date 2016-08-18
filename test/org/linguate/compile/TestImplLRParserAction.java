/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class TestImplLRParserAction implements LRParserAction
{
    private ActionType action;
    private TestImplLRParserState shiftState;
    private TestImplGrammarProduction reduceRule;

    public TestImplLRParserAction()
    {
        action = ActionType.Accept;
    }
    
    public TestImplLRParserAction(TestImplLRParserState shiftState)
    {
        action = ActionType.Shift;
        this.shiftState = shiftState;
    }

    public TestImplLRParserAction(TestImplGrammarProduction reduceRule)
    {
        action = ActionType.Reduce;
        this.reduceRule = reduceRule;
    }
    
    @Override
    public ActionType getAction() { return action; }

    @Override
    public LRParserState getShiftState() { return shiftState; }

    @Override
    public GrammarProduction getReduceRule() { return reduceRule; }
    
}
