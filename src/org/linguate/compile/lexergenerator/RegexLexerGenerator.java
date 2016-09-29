/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import org.linguate.compile.lexer.DFALexerDefinition;

/**
 *
 * @author phil
 */
public interface RegexLexerGenerator {
    DFALexerDefinition generate(RegexNode rootNode);
}
