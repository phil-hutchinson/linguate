/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxtree;

import org.linguate.compile.syntaxifier.SyntaxElement;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public interface SyntaxNodeFactory
{
    SyntaxNode CreateNodeForElement(SyntaxElement syntaxElement);
    SyntaxNode CreateNodeForLexeme(Lexeme lexeme);
}
