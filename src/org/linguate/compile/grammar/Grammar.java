/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;
import java.util.*;

/**
 * Grammar exposes the definition of a context-free grammar. A context-free grammar 
 * represents a context-free language and can be used to determine whether a given
 * string is a member of this context-free language. It also aids in the construction
 * of a parse tree for valid strings.
 * 
 * <p>A context-free grammar  consists of grammar symbols and productions. Each grammar 
 * symbol is either a terminal or a non-terminal.  One of the non-terminals is 
 * specified as the start symbol. Each production consists of a head (a single 
 * non-terminal), and a body (a list of grammar symbols). More complete explanations
 * of context-free grammars are widely available.
 * 
 * <p>Grammar producers should return read-only versions of collections (for example
 * using the unmodifiable* static methods in {@link java.util.Collections Collections}.
 * 
 * <p>Grammar consumers should treat all collections received as read-only.
 * 
 * <p>In some cases, additional restrictions will be placed on Grammar objects.
 * For instance, an LRParser accepts deterministic context-free languages, a subset
 * of all context-free languages, and so a Grammar should conform to the rules
 * for a deterministic context-free language if used by an LRParser.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Context-free_grammar">Context-Free
 * Grammar article on Wikipedia</a>
 * 
 * @author Phil Hutchinson
 */
public interface Grammar
{

    /**
     * Returns all of the terminals in the context-free grammar.
     * 
     * @return The terminals of the context-free grammar. Should be treated
     * as read-only.
     */
    List<GrammarTerminal> getTerminals();
    
    /**
     * Returns all of the non-terminals in the context-free grammar.
     * 
     * @return The non-terminals of the context-free grammar. Should be treated
     * as read-only.
     */
    List<GrammarNonTerminal> getNonTerminals();

    /**
     * Returns all of the productions in the context-free grammar.
     * 
     * <p>All of the grammar symbols used in the productions should be returned
     * by the appropriate method ({@link #getTerminals() getTerminals} or 
     * {@link #getNonTerminals() getNonTerminals}. The head of the first production
     * is considered the start symbol.
     * 
     * @return The productions of the context-free grammar. Should be treated as
     * read-only.
     */
    List<GrammarProduction> getProductions();
}
