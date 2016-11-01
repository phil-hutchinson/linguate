/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;

/**
 * A GrammarTerminal is a type of grammar symbol used by {@link org.linguate.compile.grammar.Grammar 
 * Context-Free Grammars}. It cannot appear as the head of a production rules, only
 * in the body of production rules. An input stream will also consist entirely of 
 * grammar terminals.
 * 
 * @author Phil Hutchinson
 */
public interface GrammarTerminal extends GrammarSymbol
{
    
}
