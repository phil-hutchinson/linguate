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
