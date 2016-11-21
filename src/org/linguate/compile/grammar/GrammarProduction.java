/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;

import java.util.List;

/**
 * A GrammarProduction is a rule used by {@link org.linguate.compile.grammar.Grammar 
 * Context-Free Grammars}}. It consists of a head (a single {@link org.linguate.compile.grammar.GrammarNonTerminal
 * non-terminal} and a body, which is a list of {@link org.linguate.compile.grammar.GrammarSymbol grammar symbols}
 * which includes both non-terminals and {@link org.linguate.compile.grammar.GrammarTerminal terminals}.
 * 
 * <p>Grammar producers should return read-only versions of collections (for example
 * using the unmodifiable* static methods in {@link java.util.Collections Collections}.
 * 
 * <p>Grammar consumers should treat all collections received as read-only.
* 
 * @author Phil Hutchinson
 */

public interface GrammarProduction
{

    /**
     * Returns the head of the production.
     * @return the head of the production
     */
    GrammarNonTerminal getHead();
    
    /**
     * Returns the length of the body. Convenience method.
     * @return the number of elements in the body
     */
    int getBodyLength();

    /**
     * Returns an ordered sequence of symbols representing the body of the 
     * production. Should be treated as read-only.
     * @return the body of the production
     */
    List<GrammarSymbol> getBody();
}
