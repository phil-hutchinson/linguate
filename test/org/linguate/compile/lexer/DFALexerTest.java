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
import org.linguate.compile.lexer.DFALexerWrappers.LexemeWrapper;
import org.linguate.compile.lexeme.Lexeme;

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
        new DFALexer(null);
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
        List<Lexeme> expected = new ArrayList<Lexeme>();

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex("");

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validSingleCharInput()
    {
        String source = "a";
        List<Lexeme> expected = new ArrayList<Lexeme>();
        expected.add(new LexemeWrapper(IDENTIFIER, "a"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex(source);

        checkLexOutput(expected, actual);
    }

    @Test
    public void lex_validSingleLexemeInput()
    {
        String source = "12354";
        List<Lexeme> expected = new ArrayList<Lexeme>();
        expected.add(new LexemeWrapper(INTEGER_LITERAL, "12354"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputTakesLongestMatch()
    {
        String source = "12354.343";
        List<Lexeme> expected = new ArrayList<Lexeme>();
        expected.add(new LexemeWrapper(FLOAT_LITERAL, "12354.343"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validMultiLexemeInput()
    {
        String source = "555+abc += ab343";
        List<Lexeme> expected = new ArrayList<Lexeme>();
        expected.add(new LexemeWrapper(INTEGER_LITERAL, "555"));
        expected.add(new LexemeWrapper(ADDITION_OPERATOR, "+"));
        expected.add(new LexemeWrapper(IDENTIFIER, "abc"));
        expected.add(new LexemeWrapper(WHITE_SPACE, " "));
        expected.add(new LexemeWrapper(ADDITION_ASSIGNMENT_OPERATOR, "+="));
        expected.add(new LexemeWrapper(WHITE_SPACE, " "));
        expected.add(new LexemeWrapper(IDENTIFIER, "ab343"));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex(source);

        checkLexOutput(expected, actual);
    }
    
    @Test
    public void lex_validInputRequiresRewind()
    {
        String source = "==;== ==;=";
        List<Lexeme> expected = new ArrayList<Lexeme>();
        expected.add(new LexemeWrapper(SPECIAL_OPERATOR, "==;=="));
        expected.add(new LexemeWrapper(WHITE_SPACE, " "));
        expected.add(new LexemeWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new LexemeWrapper(ASSIGNMENT_OPERATOR, "="));
        expected.add(new LexemeWrapper(SEMICOLON, ";"));
        expected.add(new LexemeWrapper(ASSIGNMENT_OPERATOR, "="));

        instance.setLexemeFactory(lexemeFactory);
        Iterable<Lexeme> actual = (Iterable<Lexeme>) instance.lex(source);

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
    
    public void checkLexOutput(Iterable<Lexeme> expected, Iterable<Lexeme> actual)
    {
        Iterator<Lexeme> eIterator = expected.iterator();
        Iterator<Lexeme> aIterator = actual.iterator();
        
        int pos = 0;
        while(eIterator.hasNext() && aIterator.hasNext())
        {
            Lexeme eLexeme = eIterator.next();
            Lexeme aLexeme = aIterator.next();
            String unmatchedElements = String.format("Element %1$d does not match on element, expected: %2$s actual %3$s", pos, eLexeme.getElement().getName(), aLexeme.getElement().getName());
            assertEquals(unmatchedElements, eLexeme.getElement(), aLexeme.getElement());
            String unmatchedContents = String.format("Element %1$d does not match on contents, expected %2$s actual %3$s", pos, eLexeme.getContents(), aLexeme.getContents());
            assertEquals(unmatchedContents, eLexeme.getContents(), aLexeme.getContents());
            pos++;
        }
        
        assertFalse("Expected result has additional ummatched items", eIterator.hasNext());
        assertFalse("Actual result has additional ummatched items", aIterator.hasNext());
    }
}
