/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
class LRParserStackState
{
    public LRParserState state;
    public DFTNode node;

    public LRParserStackState(LRParserState state, DFTNode node)
    {
        this.state = state;
        this.node = node;
    }
    
}
