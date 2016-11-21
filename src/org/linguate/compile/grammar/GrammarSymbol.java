/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;

/**
 * A GrammarSymbol is used by {@link org.linguate.compile.grammar.Grammar 
 * Context-Free Grammars}, and can be either a terminal or non-terminal. The 
 * GrammarSymbol's main purpose is to serve as the type used by the body of 
 * production rules, which can contain both terminals and non-terminals.
 * 
 * @author Phil Hutchinson
 */public interface GrammarSymbol 
{

    /**
     * Returns the name of the grammar symbol. Within a context-free grammar,
     * the names of all grammar symbols should be unique.
     * 
     * @return the name of the grammar symbol
     */
    String getName();
}
