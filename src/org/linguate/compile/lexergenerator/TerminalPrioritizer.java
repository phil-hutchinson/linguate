/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 * A TerminalPrioritizer is used to select the terminal that has the highest
 * priority, where a Regex pattern matches multiple terminals.
 * 
 * @author Phil Hutchinson
 */
public interface TerminalPrioritizer {

    /**
     * Returns the token with the highest priority.
     * 
     * Generally speaking, this method will return one of the GrammarTerminals
     * passed in through the terminals parameter, but this is not obligatory -
     * i.e. it could return an entirely new GrammarTerminal.
     * 
     * The GrammarTerminal's for a language will generally have an absolute order,
     * so that this method simply selects the terminal from the list with the 
     * highest priority. But this is also not a requirement.
     * @param terminals list of all matched GrammarTerminals. Should not  be null 
     * or empty.
     * @return the highest priority GrammarTerminal
     */
    GrammarTerminal selectHighestPriority(Set<GrammarTerminal> terminals);
}
