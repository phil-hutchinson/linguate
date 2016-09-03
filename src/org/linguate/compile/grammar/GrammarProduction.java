/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.grammar;

import java.util.List;
/**
 *
 * @author Phil Hutchinson
 */
public interface GrammarProduction
{
    GrammarNonTerminal getHead();
    int getBodyLength();
    List<GrammarSymbol> getBody();
}
