/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import org.linguate.compile.lexer.DFALexerDefinition;

/**
 * The RegexLexerGenerator is used to create a {@link org.linguate.compile.lexer.DFALexerDefinition
 * DFALexerDefinition} based on a Regex that has already been converted into tree form.
 * 
 * @author Phil Hutchinson
 */
public interface RegexLexerGenerator {

    /**
     * Generate a {@link org.linguate.compile.lexer.DFALexerDefinition DFALexerDefinition}
     * based on the provided Regex tree, TerminalPrioritizer, and definition builder.
     * 
     * <p>The actual work of creating the {@link org.linguate.compile.lexer.DFALexerDefinition 
     * DFALexerDefinition} is offloaded to a {@link org.linguate.compile.lexergenerator.DefaultDFALexerDefinitionBuilder
     * DefaultDFALexerDefinitionBuilder}.
     * 
     * @param rootNode The root {@link org.linguate.compile.lexergenerator.RegexNode
     * RegexNode} of a regex tree to be converted to a DFA.
     * @param terminalPrioritizer A terminal prioritizer, used to select which GrammarTerminal
     * should be used if the regex accepts multiple GrammarTerminal's for the same input string.
     * @param builder A DFALexerDefinitionBuilder that builds the DFALexerDefinition
     * @return The completed DFALexerDefinition, which can be used by a {@link 
     * org.linguate.compile.lexer.DFALexer DFALexer}.
     */
    DFALexerDefinition generate(RegexNode rootNode, TerminalPrioritizer terminalPrioritizer, DFALexerDefinitionBuilder builder);
}
