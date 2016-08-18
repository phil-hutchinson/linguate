/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
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
