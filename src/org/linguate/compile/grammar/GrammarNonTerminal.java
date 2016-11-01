/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;

/**
 * A GrammarNonTerminal is a type of grammar symbol used by {@link org.linguate.compile.grammar.Grammar 
 * Context-Free Grammars}. It is the only type of grammar symbol that may appear as the 
 * head of production rules, but may also appear in the body of production rules.
 * 
 * @author Phil Hutchinson
 */
public interface GrammarNonTerminal extends GrammarSymbol
{
    
}
