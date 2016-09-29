/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author phil
 */
public interface TerminalPrioritizer {
    GrammarTerminal selectHighestPriority(Set<GrammarTerminal> terminals);
}
