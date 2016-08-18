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
class LRParserStackState
{
    public LRParserState state;
    public ParseNode node;

    public LRParserStackState(LRParserState state, ParseNode node)
    {
        this.state = state;
        this.node = node;
    }
    
}
