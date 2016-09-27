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
import static org.linguate.compile.lexer.DFALexerWrappers.*;
import org.linguate.compile.lexer.DFALexerWrappers.TokenWrapper;
import org.linguate.compile.token.Token;

/**
 *
 * @author Phil Hutchinson
 */
public class DFALexerTest
{
    protected Lexer instance;
    protected static DFALexerDefinition definition;
    protected static LexemeFactory lexemeFactory;
    
    public DFALexerTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        definition = new DFALexerWrappers.LexerDefinitionWrapper();
        lexemeFactory = new LexemeFactoryWrapper();
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        instance = new DFALexer(definition);
    }
    
    @After
    public void tearDown()
    {
    }
   
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test 
    public void constructor_nullDefinition_throws()
    {
        expectedException.expect(NullPointerException.class);
    }
   
    @Test
    public void setLexemeFactory_null_throws()
    {
        expectedException.expect(NullPointerException.class);
        instance.setLexemeFactory(null);
    }

    @Test
    public void setLexemeFactory_callTwice_throws()
    {
        instance.setLexemeFactory(lexemeFactory);
        expectedException.expect(IllegalStateException.class);
        instance.setLexemeFactory(lexemeFactory);
    }
    
    @Test 
    public void lex_callBeforeSetFactory_throws()
    {
        expectedException.expect(IllegalStateException.class);
        instance.lex("");
    }

    @Test
    public void lex_nullDefinition_throws()
    {
        instance.setLexemeFactory(lexemeFactory);
        expectedException.expect(NullPointerException.class);
        instance.lex(null);
    }
    
    @Test
    public void lex_emptyInput()
    {
        List<Token> expected = new ArrayList<Token>();

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex("");

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validSingleCharInput()
    {
        String source = "a";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(IDENTIFIER, "a"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }

    @Test
    public void lex_validSingleTokenInput()
    {
        String source = "12354";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(INTEGER_LITERAL, "12354"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputTakesLongestMatch()
    {
        String source = "12354.343";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(FLOAT_LITERAL, "12354.343"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validMultiTokenInput()
    {
        String source = "555+abc += ab343";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(INTEGER_LITERAL, "555"));
        expected.add(new TokenWrapper(ADDITION_OPERATOR, "+"));
        expected.add(new TokenWrapper(IDENTIFIER, "abc"));
        expected.add(new TokenWrapper(WHITE_SPACE, " "));
        expected.add(new TokenWrapper(ADDITION_ASSIGNMENT_OPERATOR, "+="));
        expected.add(new TokenWrapper(IDENTIFIER, "ab343"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputRequiresRewind()
    {
        String source = "===== ====";
        List<Token> expected = new ArrayList<Token>();
        expected.add(new TokenWrapper(SPECIAL_OPERATOR, "==;=="));
        expected.add(new TokenWrapper(WHITE_SPACE, " "));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new TokenWrapper(SEMICOLON, ";"));
        expected.add(new TokenWrapper(ASSIGNMENT_OPERATOR, "="));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Token> actual = (Iterable<Token>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_invalidInput_throws()
    {
        String source = "abc<>";

        instance.setLexemeFactory(lexemeFactory);
        expectedException.expect(LexerException.class);
        instance.lex(source);
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
