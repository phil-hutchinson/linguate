/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public class DFALexerWrappers
{
    public static class GrammarTerminalWrapper implements GrammarTerminal
    {
        String name;

        public GrammarTerminalWrapper(String name)
        {
            this.name = name;
        }
        
        @Override
        public String getName()
        {
            return name;
        }
        
    }
    
    public static class LexerDefinitionWrapper implements DFALexerDefinition
    {

        @Override
        public int getNextState(int currentState, char character)
        {
            // Note: isLetter(), isDigit() return true for multiple alphabets and multiple types of numbers,
            // but test cases will be restricted to latin alpha A-Z,a-z and digits 0-9
            // Similarly, isSpaceChar() returns true for many different white space characters,
            // but test cases will be restricted to normal spaces.
            switch(currentState)
            {
                // state 0 - beginning new lexeme (start state)
                case 0:
                    if(Character.isLetter(character))
                    {
                        return 1;
                    }
                    else if (Character.isDigit(character))
                    {
                        return 2;
                    }
                    else if (character == '+')
                    {
                        return 5;
                    }
                    else if (character == '=')
                    {
                        return 8;
                    }
                    else if (Character.isSpaceChar(character))
                    {
                        return 13;
                    }
                    else if (character == ';')
                    {
                        return 14;
                    }
                    break;

                // state 1 - building identifier
                case 1:
                    if (Character.isLetterOrDigit(character))
                    {
                        return 1;
                    }
                    break;
                    
                // state 2 -  building int/float
                case 2:
                    if (Character.isDigit(character))
                    {
                        return 2;
                    }
                    else if (character == '.')
                    {
                        return 3;
                    }
                    break;
                    
                // state 3 - building float, immediately after decimal point
                case 3:
                    if (Character.isDigit(character))
                    {
                        return 4;
                    }
                    break;
                    
                // state 4 - building float, in decimal section (already saw digit after decimal point)
                case 4:
                    if (Character.isDigit(character))
                    {
                        return 4;
                    }
                    break;
                    
                // state 5 - saw + sign.
                case 5:
                    if (character == '+')
                    {
                        return 6;
                    }
                    else if (character == '=')
                    {
                        return 7;
                    }
                    break;
                    
                // state 6 - saw ++
                // state 7 - saw +=
                    
                // state 8 - saw =
                case 8:
                    if (character == '=')
                    {
                        return 9;
                    }
                    break;
                    
                // state 9 - saw ==
                case 9:
                    if (character == ';')
                    {
                        return 10;
                    }
                    break;
                    
                // state 10 - saw ==;
                case 10:
                    if (character == '=')
                    {
                        return 11;
                    }
                    break;
                    
                // state 11 - saw ==;=
                case 11:
                    if (character == '=')
                    {
                        return 12;
                    }
                    break;
                    
                // state 12 - saw ==;==
                    
                // state 13 - in white space
                case 13:
                    if (Character.isSpaceChar(character))
                    {
                        return 13;
                    }
                    
                // state 14 - saw semi-colon
            }
            
            return DEAD_STATE;
        }

        @Override
        public GrammarTerminal accepts(int state)
        {
            switch (state)
            {
                case 1:
                    return IDENTIFIER;
                    
                case 2:
                    return INTEGER_LITERAL;
                    
                case 4:
                    return FLOAT_LITERAL;
                    
                case 5:
                    return ADDITION_OPERATOR;
                    
                // state 6 - saw ++
                // state 7 - saw +=
                    
                // state 8 - saw =
                case 6:
                    return INCREMENT_OPERATOR;
                    
                case 7:
                    return ADDITION_ASSIGNMENT_OPERATOR;
                    
                case 8:
                    return ASSIGNMENT_OPERATOR;
                    
                case 12:
                    return SPECIAL_OPERATOR;
                    
                case 13:
                    return WHITE_SPACE;
                    
                case 14:
                    return SEMICOLON;
            }
            
            return null;
        }

        @Override
        public Set<GrammarTerminal> getAllAcceptTerminals()
        {
            GrammarTerminal terminals[] = { IDENTIFIER, INTEGER_LITERAL, FLOAT_LITERAL, ASSIGNMENT_OPERATOR,
                    ADDITION_OPERATOR, INCREMENT_OPERATOR, ADDITION_ASSIGNMENT_OPERATOR,
                    SPECIAL_OPERATOR, WHITE_SPACE, SEMICOLON};
            return new HashSet<GrammarTerminal>(Arrays.asList(terminals));
        }

        @Override
        public int getCharacterCongruency(char character) {
            throw new UnsupportedOperationException("Not required for test.");
        }

        @Override
        public int getNextState(int currentState, int characterCongruency) {
            throw new UnsupportedOperationException("Not required for test.");
        }
        
    }

    public static class LexemeWrapper implements Lexeme
    {
        GrammarTerminal element;
        String contents;

        public LexemeWrapper(GrammarTerminal element, String contents)
        {
            this.element = element;
            this.contents = contents;
        }

        @Override
        public GrammarTerminal getElement()
        {
            return element;
        }

        @Override
        public String getContents()
        {
            return contents;
        }
    }
    
    public static class LexemeFactoryWrapper implements LexemeFactory
    {

        @Override
        public Lexeme CreateLexeme(GrammarTerminal terminal, String contents)
        {
            return new LexemeWrapper(terminal, contents);
        }
        
    }
    
    public static final GrammarTerminal IDENTIFIER = new GrammarTerminalWrapper("IDENTIFIER"); // [Alpha]([Alpha]|[Digit])*
    public static final GrammarTerminal INTEGER_LITERAL = new GrammarTerminalWrapper("INT_LIT"); // [Digit]+
    public static final GrammarTerminal FLOAT_LITERAL = new GrammarTerminalWrapper("FLOAT_LIT"); // [Digit]+[Period][Digit]+
    public static final GrammarTerminal ASSIGNMENT_OPERATOR = new GrammarTerminalWrapper("ASSIGN_OP"); // "="
    public static final GrammarTerminal ADDITION_OPERATOR = new GrammarTerminalWrapper("ADD_OP"); // "+"
    public static final GrammarTerminal INCREMENT_OPERATOR = new GrammarTerminalWrapper("INCR_OP"); // "++"
    public static final GrammarTerminal ADDITION_ASSIGNMENT_OPERATOR = new GrammarTerminalWrapper("ADDASSIGN_OP"); // "+="
    public static final GrammarTerminal SPECIAL_OPERATOR = new GrammarTerminalWrapper("SPECIAL_OP"); // "==;==" (unusual operator used to test lexer rewind.)
    public static final GrammarTerminal WHITE_SPACE = new GrammarTerminalWrapper("WHITE_SPACE"); // {WhiteSpace]+
    public static final GrammarTerminal SEMICOLON = new GrammarTerminalWrapper("STATEMENT_TERMINATOR"); // ";"
    
}
