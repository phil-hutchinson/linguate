/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.compile;
import java.util.*;

/**
 *
 * @author Phil Hutchinson
 */
public interface Grammar
{
    List<GrammarTerminal> getTerminals(); // should be treated as read-only
    List<GrammarNonTerminal> getNonTerminals(); // should be treated as read-only
    List<GrammarProduction> getProductions(); // should be treated as read-only
}
