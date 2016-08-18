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
public interface LRParserAction 
{
    public enum ActionType
    {
        Accept,
        Shift,
        Reduce,
    }
    
    public ActionType getAction();
    public LRParserState getShiftState();
    public GrammarProduction getReduceRule();
}
