/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import org.linguate.compile.LexerException;
import org.linguate.compile.grammar.GrammarTerminal;
import static org.linguate.compile.lexer.DFALexerDefinition.DEAD_STATE;
import org.linguate.compile.token.Token;

/**
 *
 * @author Phil Hutchinson
 */
public class DFALexer implements Lexer
{
    protected interface SourceReader
    {
        boolean hasCharAt(int offset);
        char getCharAt(int offset);
        String getSequence(int startOffset, int length);
        void canFree(int freeableToOffset);
    }
    
    private DFALexerDefinition definition;
    private LexemeFactory lexemeFactory;
    
    public DFALexer(DFALexerDefinition definition)
    {
        if (definition == null)
        {
            throw new NullPointerException("Lexer Definition cannot be null.");
        }
        
        this.definition = definition;
    }
    
    @Override
    public void setLexemeFactory(LexemeFactory lexemeFactory)
    {
        if (lexemeFactory == null)
        {
            throw new NullPointerException("Lexeme Factory cannot be null.");
        }
        
        if (this.lexemeFactory != null)
        {
            throw new IllegalStateException("Lexeme Factory cannot be set twice.");
        }
        
        this.lexemeFactory = lexemeFactory;
    }

    @Override
    public Iterable<? extends Token> lex(String source)
    {
        if (definition == null)
        {
            throw new IllegalStateException("Lexer Definition must be set before calling lex()");
        }
        if (source == null)
        {
            throw new NullPointerException("Cannot lex a null source");
        }
        
        return lex(new StringSourceReader(source));
    }
    
    protected Iterable<? extends Token> lex(SourceReader sourceReader)
    {
        ArrayList<Token> result = new ArrayList<Token>();
        
        int currSequenceOffset = 0;
        int nextCharOffset = 0;
        int currState = 0;
        boolean reachedDeadState = false;
        int longestAcceptEndOffset = 0;
        GrammarTerminal longestAcceptTerminal = null;
        
        while (sourceReader.hasCharAt(currSequenceOffset))
        {
            if (sourceReader.hasCharAt(nextCharOffset))
            {
                char nextChar = sourceReader.getCharAt(nextCharOffset);
                int nextState = definition.getNextState(currState, nextChar);
                if (nextState == DEAD_STATE)
                {
                    reachedDeadState = true;
                }
                else
                {
                    currState = nextState;
                    nextCharOffset++;
                    GrammarTerminal currStateAccepts = definition.accepts(currState);
                    if (currStateAccepts != null)
                    {
                        longestAcceptTerminal = currStateAccepts;
                        longestAcceptEndOffset = nextCharOffset;
                    }
                }
            }
            else
            {
                reachedDeadState = true;
            }
            if (reachedDeadState)
            {
                if (longestAcceptTerminal == null)
                {
                    throw new LexerException("Encountered input with no matching token.");
                }
                else
                {
                    String contents = sourceReader.getSequence(currSequenceOffset, longestAcceptEndOffset);
                    result.add(lexemeFactory.CreateLexeme(longestAcceptTerminal, contents));
                    currSequenceOffset = longestAcceptEndOffset;
                    currState = 0;
                    nextCharOffset = currSequenceOffset;
                    reachedDeadState = false;
                    longestAcceptEndOffset = 0;
                    longestAcceptTerminal = null;                
                }
            }
        }
        
        return result;
    }
    
    private static class StringSourceReader implements DFALexer.SourceReader
    {
        private String source;
        
        public StringSourceReader(String source)
        {
            this.source = source;
        }

        @Override public boolean hasCharAt(int offset)
        {
            return (source.length() > offset);
        }

        @Override
        public char getCharAt(int offset)
        {
            return (source.charAt(offset));
        }

        @Override
        public String getSequence(int startOffset, int endOffset)
        {
            return (source.substring(startOffset, endOffset));
        }

        @Override
        public void canFree(int freeableToOffset)
        {
            // do nothing: for string source, freeing isn't convenient.
        }

    }
}
