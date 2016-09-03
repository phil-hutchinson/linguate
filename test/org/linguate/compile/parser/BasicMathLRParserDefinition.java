/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathLRParserDefinition implements LRParserDefinition
{
    private LRParserState startState;

    public BasicMathLRParserDefinition(LRParserState startState)
    {
        this.startState = startState;
    }
    
    @Override
    public LRParserState getStartState()
    {
        return startState;
    }
    
}
