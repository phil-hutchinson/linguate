/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

/**
 * An LRParserDefinition represents a set of rules that are used to drive an
 * {@link org.linguate.compile.parser.LRParser LRParser}. Generally, the same
 * parser can be used for any LR-compliant grammar - it is only the parser
 * definition that changes.
 * 
 * <p>The parser definition provides only the start state, which can be used
 * iteratively to gain information about all other states.
 * 
 * @author Phil Hutchinson
 */
public interface LRParserDefinition
{

    /**
     * Provides the {@link org.linguate.compile.parser.LRParserState LRParserState}
     * that represents the start state of the definition, and through which all
     * other parser definition information can be acquired.
     * @return the start state for the parser definition
     */
    LRParserState getStartState();
}
