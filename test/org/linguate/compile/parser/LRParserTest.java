/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.testimpl.basicmath.BasicMathToken;
import org.linguate.compile.testimpl.basicmath.BasicMathDFTNode;
import org.linguate.compile.testimpl.basicmath.BasicMathParserDefinition;
import org.linguate.compile.testimpl.basicmath.BasicMathDFTNodeFactory;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.ParserException;
import static org.linguate.compile.testimpl.basicmath.BasicMathParserDefinition.*;

/**
 *
 * @author Phil Hutchinson
 */
public class LRParserTest
{
    public LRParserTest()
    {
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @BeforeClass
    public static void setUpClass()
    {
        basicMathParseNodeFactory = new BasicMathDFTNodeFactory();
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    static BasicMathDFTNodeFactory basicMathParseNodeFactory;
    
    @Test
    public void Parse_NullInput() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = null;
        DFTNode expectedResult = null;
        DFTNode result = instance.Parse(input);
        assertEquals("Null input should produce null output.", expectedResult, result);
    }
    
    @Test
    public void Parse_EmptyInput() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        DFTNode expectedResult = null;
        DFTNode result = instance.Parse(input);
        assertEquals("Null input should produce null output.", expectedResult, result);
    }
    
    @Test
    public void Parse_SingleNumber_CheckTree() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        assertEquals("Element 0 (root element) should be Expression.", result.getElement(), EXPRESSION);
        BasicMathDFTNode ele0_0 = result.children.get(0);
        assertEquals("Element 0,0 should be Term.", ele0_0.getElement(), TERM);
        BasicMathDFTNode ele0_0_0 = ele0_0.children.get(0);
        assertEquals("Element 0,0,0 should be Factor.", ele0_0_0.getElement(), FACTOR);
        BasicMathDFTNode ele0_0_0_0 = ele0_0_0.children.get(0);
        assertEquals("Element 0,0,0,0 should be Numeric Litearl.", ele0_0_0_0.getElement(), NUMERIC_LITERAL);
    }
    
    @Test
    public void Parse_SimpleAddition_CheckTree() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        assertEquals("Element 0 (root element) should be Expression.", result.getElement(), EXPRESSION);
        BasicMathDFTNode ele0_0 = result.children.get(0);
        assertEquals("Element 0,0 should be Expression.", ele0_0.getElement(), EXPRESSION);
        BasicMathDFTNode ele0_0_0 = ele0_0.children.get(0);
        assertEquals("Element 0,0,0 should be Term.", ele0_0_0.getElement(), TERM);
        BasicMathDFTNode ele0_0_0_0 = ele0_0_0.children.get(0);
        assertEquals("Element 0,0,0,0 should be Factor.", ele0_0_0_0.getElement(), FACTOR);
        BasicMathDFTNode ele0_0_0_0_0 = ele0_0_0_0.children.get(0);
        assertEquals("Element 0,0,0,0,0 should be Numeric Literal.", ele0_0_0_0_0.getElement(), NUMERIC_LITERAL);
        BasicMathDFTNode ele0_1 = result.children.get(1);
        assertEquals("Element 0,1 should be Addition Operator.", ele0_1.getElement(), ADDITION_OPERATOR);
        BasicMathDFTNode ele0_2 = result.children.get(2);
        assertEquals("Element 0,2 should be Term.", ele0_2.getElement(), TERM);
        BasicMathDFTNode ele0_2_0 = ele0_2.children.get(0);
        assertEquals("Element 0,2,0 should be Factor.", ele0_2_0.getElement(), FACTOR);
        BasicMathDFTNode ele0_2_0_0 = ele0_2_0.children.get(0);
        assertEquals("Element 0,2,0,0 should be Numeric Literal.", ele0_2_0_0.getElement(), NUMERIC_LITERAL);
    }
    
    @Test
    public void Parse_SimpleAddition_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL, "18"));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "7"));
        int expectedValue = 18 + 7;
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_SimpleMultiplication_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL, "8"));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "7"));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        int expectedValue = 8 * 7;
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_OrderOfOperations_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL, "5"));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "9"));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        int expectedValue = 5 + 2 * 9;
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_Brackets_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "5"));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "9"));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        int expectedValue = (5 + 2) * 9;
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_ComplexExpression_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "1"));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "5"));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "9"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "1"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        int expectedValue = (1+5*2)*(9)*(2+1);
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_NestedBrackets_EvaluateValue() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(LEFT_BRACKET));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "1"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "1"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(MULTIPLICATION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "2"));
        input.add(new BasicMathToken(RIGHT_BRACKET));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        input.add(new BasicMathToken(NUMERIC_LITERAL, "1"));
        BasicMathDFTNode result = (BasicMathDFTNode) instance.Parse(input);
        int expectedValue = (((((2)+1)*2)+1)*2)+1;
        assertEquals(expectedValue, result.value);
    }
    
    @Test
    public void Parse_InvalidInput_Throws() throws ParserException
    {
        LRParser instance = new LRParser(BasicMathParserDefinition.definition, basicMathParseNodeFactory);
        ArrayList<BasicMathToken> input = new ArrayList<>();
        input.add(new BasicMathToken(NUMERIC_LITERAL));
        input.add(new BasicMathToken(ADDITION_OPERATOR));
        expectedException.expect(ParserException.class);
        instance.Parse(input);
    }
    
    
}
