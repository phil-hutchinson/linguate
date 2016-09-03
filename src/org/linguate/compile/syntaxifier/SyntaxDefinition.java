/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import org.linguate.compile.grammar.GrammarProduction;
import java.util.Map;

/**
 *
 * @author Phil Hutchinson
 */
public interface SyntaxDefinition
{
    Map<GrammarProduction, SyntaxRule> getRuleMapping();
}
