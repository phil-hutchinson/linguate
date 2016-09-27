/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.linguate.compile.LexerException;
import static org.linguate.compile.lexer.DefaultLexerWrappers.*;
import org.linguate.compile.lexer.DefaultLexerWrappers.TokenWrapper;
import org.linguate.compile.token.Token;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultLexerTest
{
    protected Lexer instance;
    protected static LexerDefinition definition;
    
    public DefaultLexerTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        definition = new DefaultLexerWrappers.LexerDefinitionWrapper();
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        instance = new DefaultLexer();
    }
    
    @After
    public void tearDown()
    {
    }
   
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void setLexerDefinition_null_throws()
    {
        expectedException.expect(NullPointerException.class);
        instance.setLexerDefinition(null);
    }

    @Test
    public void setLexerDefinition_notNull()
    {
        instance.setLexerDefinition(definition);
        // can only test successful creation i.e. no exception thrown.
    }
    
    @Test
    public void setLexerDefinition_callTwice_throws()
    {
        instance.setLexerDefinition(definition);
        expectedException.expect(IllegalStateException.class);
        instance.setLexerDefinition(definition);
    }
    
    @Test 
    public void lex_callBeforeSetDefinition_throws()
    {
        expectedException.expect(IllegalStateException.class);
        instance.lex(new ArrayList<Character>());
    }

    @Test
    public void lex_nullDefinition_throws()
    {
        instance.setLexerDefinition(definition);
        expectedException.expect(NullPointerException.class);
        instance.lex(null);
    }
    
    @Test
    public void lex_emptyInput()
    {
        ArrayList<Character> source = new ArrayList<Character>();
        List<Token> expected = new ArrayList<Token>();

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validSingleCharInput()
    {
        ArrayList<Character> source = new ArrayList<Character>();
        source.add('a');
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(IDENTIFIER, "a"));

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validSingleTokenInput()
    {
        ArrayList<Character> source = buildLexInput("12354");
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(INTEGER_LITERAL, "12354"));

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputTakesLongestMatch()
    {
        ArrayList<Character> source = buildLexInput("12354.343");
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(FLOAT_LITERAL, "12354.343"));

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validMultiTokenInput()
    {
        ArrayList<Character> source = buildLexInput("555+abc += ab343");
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(INTEGER_LITERAL, "555"));
        expected.add(new TokenWrapper(ADDITION_OPERATOR, "+"));
        expected.add(new TokenWrapper(IDENTIFIER, "abc"));
        expected.add(new TokenWrapper(WHITE_SPACE, " "));
        expected.add(new TokenWrapper(ADDITION_ASSIGNMENT_OPERATOR, "+="));
        expected.add(new TokenWrapper(IDENTIFIER, "ab343"));

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputRequiresRewind()
    {
        ArrayList<Character> source = buildLexInput("===== ====");
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(FIVE_EQUALS_OPERATOR, "====="));
        expected.add(new TokenWrapper(WHITE_SPACE, " "));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));

        instance.setLexerDefinition(definition);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_invalidInput_throws()
    {
        ArrayList<Character> source = buildLexInput("abc<>");

        instance.setLexerDefinition(definition);
        expectedException.expect(LexerException.class);
        instance.lex(source);
    }
    
    public ArrayList<Character> buildLexInput(String str)
    {
        ArrayList<Character> result = new ArrayList<>();
        for(int strPos = 0; strPos < str.length(); strPos++)
        {
            char charAtPos = str.charAt(strPos);
            Character charObj = new Character(charAtPos);
            result.add(charObj);
        }
        return result;
    }
    
    public void checkLexOutput(Iterable<Token> expected, Iterable<Token> actual)
    {
        Iterator<Token> eIterator = expected.iterator();
        Iterator<Token> aIterator = actual.iterator();
        
        int pos = 0;
        while(eIterator.hasNext() && aIterator.hasNext())
        {
            Token eToken = eIterator.next();
            Token aToken = aIterator.next();
            String unmatchedElements = String.format("Element %1$d does not match on element, expected: %2s actual %3s", pos, eToken.getElement().getName(), aToken.getElement().getName());
            assertEquals(unmatchedElements, eToken.getElement(), aToken.getElement());
            String unmatchedContents = String.format("Element %1$d does not match on contents, expected: %2s actual %3s", pos, eToken.getContents(), aToken.getContents());
            assertEquals(unmatchedContents, eToken.getContents(), aToken.getContents());
            pos++;
        }
        
        assertFalse("Expected result has additional ummatched items", eIterator.hasNext());
        assertFalse("Actual result has additional ummatched items", aIterator.hasNext());
    }
}
