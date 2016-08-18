/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class TestImplLRParserDefinition implements LRParserDefinition
{
    private LRParserState startState;

    public TestImplLRParserDefinition(LRParserState startState)
    {
        this.startState = startState;
    }
    
    @Override
    public LRParserState getStartState()
    {
        return startState;
    }
    
}
