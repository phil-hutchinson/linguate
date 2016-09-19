/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.syntaxtree.SyntaxNode;
import static org.linguate.compile.syntaxifier.BasicMathSyntaxDefinition.*;

/**
 *
 * @author Phil Hutchinson
 */
public class SyntaxifierTest
{
    
    public SyntaxifierTest()
    {
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        basicMathSyntaxDefinition = new BasicMathSyntaxDefinition();
        basicMathSyntaxNodeFactory = new BasicMathSyntaxNodeFactory();
    }
    
    @After
    public void tearDown()
    {
    }

    BasicMathSyntaxDefinition basicMathSyntaxDefinition;
    BasicMathSyntaxNodeFactory basicMathSyntaxNodeFactory;
    
    private List<DFTNode> buildChildren(DFTNode... nodes)
    {
        return Arrays.asList(nodes);
    }
    
    @Test
    public void Syntaxify_Constructor2_NullSyntaxDefinition_Throws()
    {
        expectedException.expect(NullPointerException.class);
        new Syntaxifier(null, basicMathSyntaxNodeFactory);
    }
    
    @Test
    public void Syntaxify_Constructor2_NullSyntaxNodeFactory_Throws()
    {
        expectedException.expect(NullPointerException.class);
        new Syntaxifier(basicMathSyntaxDefinition, null);
    }

    @Test
    public void testSyntaxify_SingleTokenBasic()
    {
        Syntaxifier syntaxifier = new Syntaxifier(basicMathSyntaxDefinition, basicMathSyntaxNodeFactory);
        BasicMathToken numLit = new BasicMathToken(NUMERIC_LITERAL, "6");
        DFTNode leafNode = syntaxifier.CreateLeafNode(numLit);
        DFTNode innerNode = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(leafNode));
        SyntaxNode resultTree = syntaxifier.GetTree(innerNode);
        String expected = NUMERIC_LITERAL.getName();
        String actual = resultTree.getElement().getName();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSyntaxify_SingleTokenFullTraversal()
    {
        // expression: 12
        Syntaxifier syntaxifier = new Syntaxifier(basicMathSyntaxDefinition, basicMathSyntaxNodeFactory);
        BasicMathToken numLit = new BasicMathToken(NUMERIC_LITERAL, "12");
        
        DFTNode n1 = syntaxifier.CreateLeafNode(numLit);
        DFTNode n2 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(n1));
        DFTNode n3 = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(n2));
        DFTNode n4 = syntaxifier.CreateInnerNode(REDUCE_T_TO_E, buildChildren(n3));
        SyntaxNode resultTree = syntaxifier.GetTree(n4);
        String expected = NUMERIC_LITERAL.getName();
        String actual = resultTree.getElement().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void testSyntaxify_BracketedToken()
    {
        // expression: ( 100 )
        Syntaxifier syntaxifier = new Syntaxifier(basicMathSyntaxDefinition, basicMathSyntaxNodeFactory);
        BasicMathToken leftBracket = new BasicMathToken(LEFT_BRACKET, "(");
        BasicMathToken numLit = new BasicMathToken(NUMERIC_LITERAL, "100");
        BasicMathToken rightBracket = new BasicMathToken(RIGHT_BRACKET, ")");
        
        DFTNode leftBracketLeaf = syntaxifier.CreateLeafNode(leftBracket);
        DFTNode numLitLeaf = syntaxifier.CreateLeafNode(numLit);
        DFTNode factorInsideBrackets = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLitLeaf));
        DFTNode termInsideBrackets = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factorInsideBrackets));
        DFTNode expressionInsideBrackets = syntaxifier.CreateInnerNode(REDUCE_T_TO_E, buildChildren(termInsideBrackets));
        DFTNode rightBracketLeaf = syntaxifier.CreateLeafNode(rightBracket);
        DFTNode factorFull = syntaxifier.CreateInnerNode(REDUCE_LER_TO_F, buildChildren(leftBracketLeaf, expressionInsideBrackets, rightBracketLeaf));
        DFTNode termFull = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factorFull));
        DFTNode expressionFull = syntaxifier.CreateInnerNode(REDUCE_T_TO_E, buildChildren(termFull));
        SyntaxNode resultTree = syntaxifier.GetTree(expressionFull);
        String expected = NUMERIC_LITERAL.getName();
        String actual = resultTree.getElement().getName();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSyntaxify_LeftLeaningTree()
    {
        // expression 10 * 7 + 5
        
        Syntaxifier syntaxifier = new Syntaxifier(basicMathSyntaxDefinition, basicMathSyntaxNodeFactory);
        BasicMathToken numLit10 = new BasicMathToken(NUMERIC_LITERAL, "10");
        BasicMathToken multiplication = new BasicMathToken(MULTIPLICATION_OPERATOR, "*");
        BasicMathToken numLit7 = new BasicMathToken(NUMERIC_LITERAL, "7");
        BasicMathToken addition = new BasicMathToken(ADDITION_OPERATOR, "+");
        BasicMathToken numLit5 = new BasicMathToken(NUMERIC_LITERAL, "5");
        
        DFTNode numLit10Leaf = syntaxifier.CreateLeafNode(numLit10);
        DFTNode factor10 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit10Leaf));
        DFTNode term10 = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factor10));
        DFTNode multiplicationLeaf = syntaxifier.CreateLeafNode(multiplication);
        DFTNode numLit7Leaf = syntaxifier.CreateLeafNode(numLit7);
        DFTNode factor7 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit7Leaf));
        DFTNode term10times7 = syntaxifier.CreateInnerNode(REDUCE_TMF_TO_T, buildChildren(term10, multiplicationLeaf, factor7));
        DFTNode expression10times7 = syntaxifier.CreateInnerNode(REDUCE_T_TO_E, buildChildren(term10times7));
        DFTNode additionLeaf = syntaxifier.CreateLeafNode(addition);
        DFTNode numLit5Leaf = syntaxifier.CreateLeafNode(numLit5);
        DFTNode factor5 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit5Leaf));
        DFTNode term5 = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factor5));
        DFTNode expression10times7plus5 = syntaxifier.CreateInnerNode(REDUCE_EAT_TO_E, buildChildren(expression10times7, additionLeaf, term5));
        
        BasicMathSyntaxNode resultTree = (BasicMathSyntaxNode) syntaxifier.GetTree(expression10times7plus5);
        
        assertEquals(ADDITION_OPERATOR.getName(), resultTree.getElement().getName());
        assertEquals(2, resultTree.getChildren().size());
        
        BasicMathSyntaxNode child0 = resultTree.getChildren().get(0);
        assertEquals(MULTIPLICATION_OPERATOR.getName(), child0.getElement().getName());
        assertEquals(2, child0.getChildren().size());
        
        BasicMathSyntaxNode child0_0 = child0.getChildren().get(0);
        assertEquals(NUMERIC_LITERAL.getName(), child0_0.getElement().getName());
        assertEquals(10, child0_0.getValue());
        assertEquals(0, child0_0.getChildren().size());
        
        BasicMathSyntaxNode child0_1 = child0.getChildren().get(1);
        assertEquals(NUMERIC_LITERAL.getName(), child0_1.getElement().getName());
        assertEquals(7, child0_1.getValue());
        assertEquals(0, child0_1.getChildren().size());
        
        BasicMathSyntaxNode child1 = resultTree.getChildren().get(1);
        assertEquals(NUMERIC_LITERAL.getName(), child1.getElement().getName());
        assertEquals(5, child1.getValue());
        assertEquals(0, child1.getChildren().size());
    }

    @Test
    public void testSyntaxify_RightLeaningTree()
    {
        // expression 8 + 6 * 4
        
        Syntaxifier syntaxifier = new Syntaxifier(basicMathSyntaxDefinition, basicMathSyntaxNodeFactory);
        BasicMathToken numLit8 = new BasicMathToken(NUMERIC_LITERAL, "8");
        BasicMathToken addition = new BasicMathToken(ADDITION_OPERATOR, "+");
        BasicMathToken numLit6 = new BasicMathToken(NUMERIC_LITERAL, "6");
        BasicMathToken multiplication = new BasicMathToken(MULTIPLICATION_OPERATOR, "*");
        BasicMathToken numLit4 = new BasicMathToken(NUMERIC_LITERAL, "4");
        
        DFTNode numLit8Leaf = syntaxifier.CreateLeafNode(numLit8);
        DFTNode factor8 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit8Leaf));
        DFTNode term8 = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factor8));
        DFTNode expression8 = syntaxifier.CreateInnerNode(REDUCE_T_TO_E, buildChildren(term8));
        DFTNode additionLeaf = syntaxifier.CreateLeafNode(addition);
        DFTNode numLit6Leaf = syntaxifier.CreateLeafNode(numLit6);
        DFTNode factor6 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit6Leaf));
        DFTNode term6 = syntaxifier.CreateInnerNode(REDUCE_F_TO_T, buildChildren(factor6));
        DFTNode multiplicationLeaf = syntaxifier.CreateLeafNode(multiplication);
        DFTNode numLit4Leaf = syntaxifier.CreateLeafNode(numLit4);
        DFTNode factor4 = syntaxifier.CreateInnerNode(REDUCE_N_TO_F, buildChildren(numLit4Leaf));
        DFTNode term6times4 = syntaxifier.CreateInnerNode(REDUCE_TMF_TO_T, buildChildren(term6, multiplicationLeaf, factor4));
        DFTNode expression8plus6times4 = syntaxifier.CreateInnerNode(REDUCE_EAT_TO_E, buildChildren(expression8, additionLeaf, term6times4));
        
        BasicMathSyntaxNode resultTree = (BasicMathSyntaxNode) syntaxifier.GetTree(expression8plus6times4);
        assertEquals(ADDITION_OPERATOR.getName(), resultTree.getElement().getName());
        assertEquals(2, resultTree.getChildren().size());
        
        BasicMathSyntaxNode child0 = resultTree.getChildren().get(0);
        assertEquals(NUMERIC_LITERAL.getName(), child0.getElement().getName());
        assertEquals(8, child0.getValue());
        assertEquals(0, child0.getChildren().size());
        
        BasicMathSyntaxNode child1 = resultTree.getChildren().get(1);
        assertEquals(MULTIPLICATION_OPERATOR.getName(), child1.getElement().getName());
        assertEquals(2, child1.getChildren().size());

        BasicMathSyntaxNode child1_0 = child1.getChildren().get(0);
        assertEquals(NUMERIC_LITERAL.getName(), child1_0.getElement().getName());
        assertEquals(6, child1_0.getValue());
        assertEquals(0, child1_0.getChildren().size());
        
        BasicMathSyntaxNode child1_1 = child1.getChildren().get(1);
        assertEquals(NUMERIC_LITERAL.getName(), child1_1.getElement().getName());
        assertEquals(4, child1_1.getValue());
        assertEquals(0, child1_1.getChildren().size());
    }
}
