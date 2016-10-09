/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexer.DFALexerDefinition;
import static org.linguate.compile.lexer.DFALexerDefinition.DEAD_STATE;
import org.linguate.compile.lexergenerator.DefaultRegexLexerGeneratorWrappers.*;
import static org.linguate.compile.lexergenerator.RegexNode.NodeType.*;

/**
 *
 * @author phil
 */
public class DefaultRegexLexerGeneratorTest 
{
    protected DefaultRegexLexerGenerator instance;
    protected DefaultDFALexerDefinitionBuilder builder;
            
    public DefaultRegexLexerGeneratorTest() 
    {
    }
    
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
        instance = new DefaultRegexLexerGenerator();
        builder = new  DefaultDFALexerDefinitionBuilder();
    }
    
    @After
    public void tearDown() 
    {
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void generate_nullSyntaxNode_throws()
    {
        expectedException.expect(NullPointerException.class);
        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(new ArrayList<GrammarTerminal>());
        instance.generate(null, prioritizer, builder);
    }
    
    @Test
    public void generate_nullPrioritizer_throws()
    {
        expectedException.expect(NullPointerException.class);
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper mainLeaf = RegexNodeWrapper.CreateRegularLeaf("a");
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(mainLeaf, acceptLeaf);
        instance.generate(rootNode, null, builder);
    }
    
    
    @Test
    public void generate_simpleExpression()
    {
        // regex string: "a"
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper mainLeaf = RegexNodeWrapper.CreateRegularLeaf("a");
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(mainLeaf, acceptLeaf);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", terminal);
        testCases.put("b", null);
        testCases.put("aa", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_catNode()
    {
        // regex string: "ab"
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper aLeaf = RegexNodeWrapper.CreateRegularLeaf("a");
        RegexNodeWrapper bLeaf = RegexNodeWrapper.CreateRegularLeaf("b");
        RegexNodeWrapper abNode = RegexNodeWrapper.CreateCatNode(aLeaf, bLeaf);
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(abNode, acceptLeaf);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", null);
        testCases.put("b", null);
        testCases.put("aa", null);
        testCases.put("ab", terminal);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
        
    }
    
    @Test
    public void generate_orNode()
    {
        // regex string: "a|b" (no character set)
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper aLeaf = RegexNodeWrapper.CreateRegularLeaf("a");
        RegexNodeWrapper bLeaf = RegexNodeWrapper.CreateRegularLeaf("b");
        RegexNodeWrapper abNode = RegexNodeWrapper.CreateOrNode(aLeaf, bLeaf);
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(abNode, acceptLeaf);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", terminal);
        testCases.put("b", terminal);
        testCases.put("aa", null);
        testCases.put("ab", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_starNode()
    {
        // regex string: "a*"
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper aLeaf = RegexNodeWrapper.CreateRegularLeaf("a");
        RegexNodeWrapper aStarNode = RegexNodeWrapper.CreateStarNode(aLeaf);
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(aStarNode, acceptLeaf);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", terminal);
        testCases.put("b", null);
        testCases.put("aa", terminal);
        testCases.put("ab", null);
        testCases.put("aa", terminal);
        testCases.put("aaa", terminal);
        testCases.put("aaaa", terminal);
        testCases.put("aaaaaaaaaaaaaaa", terminal);
        testCases.put("", terminal); // we will accept here, although lexer will ignore.

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_characterSet()
    {
        // regex string: "[a-f]"
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper mainLeaf = RegexNodeWrapper.CreateRegularLeaf("abcdef");
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(mainLeaf, acceptLeaf);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", terminal);
        testCases.put("b", terminal);
        testCases.put("c", terminal);
        testCases.put("d", terminal);
        testCases.put("e", terminal);
        testCases.put("f", terminal);
        testCases.put("g", null);
        testCases.put("h", null);
        testCases.put("i", null);
        testCases.put("j", null);
        testCases.put("aa", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_multipleCharacterSet()
    {
        // regex string: "([a-f][cd])|([cd][a-f])" i.e. two letters from alphabet a-f, at least one c or d.
        GrammarTerminalWrapper terminal = new GrammarTerminalWrapper("SampleTerminal");
        RegexNodeWrapper leaf1_1 = RegexNodeWrapper.CreateRegularLeaf("abcdef");
        RegexNodeWrapper leaf1_2 = RegexNodeWrapper.CreateRegularLeaf("cd");
        RegexNodeWrapper cat1 = RegexNodeWrapper.CreateCatNode(leaf1_1, leaf1_2);
        RegexNodeWrapper leaf2_1 = RegexNodeWrapper.CreateRegularLeaf("cd");
        RegexNodeWrapper leaf2_2 = RegexNodeWrapper.CreateRegularLeaf("abcdef");
        RegexNodeWrapper cat2 = RegexNodeWrapper.CreateCatNode(leaf2_1, leaf2_2);
        RegexNodeWrapper orNode = RegexNodeWrapper.CreateOrNode(cat1, cat2);
        RegexNodeWrapper acceptLeaf = RegexNodeWrapper.CreateAcceptLeaf(terminal);
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateCatNode(orNode, acceptLeaf);

        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("ac", terminal);
        testCases.put("cc", terminal);
        testCases.put("cd", terminal);
        testCases.put("af", null);
        testCases.put("bb", null);
        testCases.put("abd", null);
        testCases.put("cf", terminal);
        testCases.put("fc", terminal);
        testCases.put("gc", null);
        testCases.put("c", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(terminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_multipleAccept()
    {
        // Regex: alpha "[a-c]" numeric "[1-3]" operation "[+-*/]"
        GrammarTerminalWrapper alphaTerminal = new GrammarTerminalWrapper("Alpha");
        GrammarTerminalWrapper numericTerminal = new GrammarTerminalWrapper("Numeric");
        GrammarTerminalWrapper operationTerminal = new GrammarTerminalWrapper("Operation");
        
        RegexNodeWrapper alphaLeaf = RegexNodeWrapper.CreateRegularLeaf("abc");
        RegexNodeWrapper alphaAccept = RegexNodeWrapper.CreateAcceptLeaf(alphaTerminal);
        RegexNodeWrapper alphaRoot = RegexNodeWrapper.CreateCatNode(alphaLeaf, alphaAccept);
        
        RegexNodeWrapper numericLeaf = RegexNodeWrapper.CreateRegularLeaf("123");
        RegexNodeWrapper numericAccept = RegexNodeWrapper.CreateAcceptLeaf(numericTerminal);
        RegexNodeWrapper numericRoot = RegexNodeWrapper.CreateCatNode(numericLeaf, numericAccept);
        
        RegexNodeWrapper operationLeaf = RegexNodeWrapper.CreateRegularLeaf("+-*/");
        RegexNodeWrapper operationAccept = RegexNodeWrapper.CreateAcceptLeaf(operationTerminal);
        RegexNodeWrapper operationRoot = RegexNodeWrapper.CreateCatNode(operationLeaf, operationAccept);
        
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateOrNode(alphaRoot, numericRoot, operationRoot);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("a", alphaTerminal);
        testCases.put("b", alphaTerminal);
        testCases.put("c", alphaTerminal);
        testCases.put("1", numericTerminal);
        testCases.put("2", numericTerminal);
        testCases.put("3", numericTerminal);
        testCases.put("+", operationTerminal);
        testCases.put("-", operationTerminal);
        testCases.put("*", operationTerminal);
        testCases.put("/", operationTerminal);
        testCases.put("aa", null);
        testCases.put("bb", null);
        testCases.put("123", null);
        testCases.put("a1", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(alphaTerminal, numericTerminal, operationTerminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    @Test
    public void generate_prioritizedAccept()
    {
        // Regex: keyword "if" identifier "[a-z]+" == "[a-z][a-z]*"
        GrammarTerminalWrapper keywordTerminal = new GrammarTerminalWrapper("Keyword");
        GrammarTerminalWrapper identifierTerminal = new GrammarTerminalWrapper("Identifier");
        RegexNodeWrapper keywordLeaf1 = RegexNodeWrapper.CreateRegularLeaf("i");
        RegexNodeWrapper keywordLeaf2 = RegexNodeWrapper.CreateRegularLeaf("f");
        RegexNodeWrapper ifInner = RegexNodeWrapper.CreateCatNode(keywordLeaf1, keywordLeaf2);
        RegexNodeWrapper keywordAccept = RegexNodeWrapper.CreateAcceptLeaf(keywordTerminal);
        RegexNodeWrapper keywordRoot = RegexNodeWrapper.CreateCatNode(ifInner, keywordAccept);
        
        RegexNodeWrapper idFirstLeaf = RegexNodeWrapper.CreateRegularLeaf("abcdefghijklmnopqrstuvwxyz");
        RegexNodeWrapper idSecondLeaf = RegexNodeWrapper.CreateRegularLeaf("abcdefghijklmnopqrstuvwxyz");
        RegexNodeWrapper idStar = RegexNodeWrapper.CreateStarNode(idSecondLeaf);
        RegexNodeWrapper idInner = RegexNodeWrapper.CreateCatNode(idFirstLeaf, idSecondLeaf);
        RegexNodeWrapper idAccept = RegexNodeWrapper.CreateAcceptLeaf(identifierTerminal);
        RegexNodeWrapper idRoot = RegexNodeWrapper.CreateCatNode(idInner, idAccept);
        
        RegexNodeWrapper rootNode = RegexNodeWrapper.CreateOrNode(keywordRoot, idRoot);
        
        Map<String, GrammarTerminal> testCases = new HashMap<String, GrammarTerminal>();
        testCases.put("if", keywordTerminal);
        testCases.put("fi", identifierTerminal);
        testCases.put("iif", null);
        testCases.put("ifif", null);
        testCases.put("iff", null);
        testCases.put("123", null);
        testCases.put("", null);

        TerminalPrioritizer prioritizer = new TerminalPrioritizerWrapper(keywordTerminal, identifierTerminal);
        DFALexerDefinition definition = instance.generate(rootNode, prioritizer, builder);
        
        verify(definition, testCases);
    }
    
    public static void verify(DFALexerDefinition definition, Map<String, GrammarTerminal> testInputSet)
    {
        for (Entry<String,GrammarTerminal> testInput : testInputSet.entrySet())
        {
            char[] inputSymbols = testInput.getKey().toCharArray();
            GrammarTerminal expectedResult = testInput.getValue();
            
            int currState = 0;
            boolean stillWorking = true;
            
            for(int symbolPos = 0; symbolPos < inputSymbols.length && stillWorking; symbolPos++)
            {
                char currSymbol = inputSymbols[symbolPos];
                currState = definition.getNextState(currState, currSymbol);
                if (currState == DEAD_STATE)
                {
                    stillWorking = false;
                }
            }
            
            GrammarTerminal actualResult = (currState != DEAD_STATE)
                    ? definition.accepts(currState)
                    : null;
            
            String assertMsg = String.format("Unmatched input: %1$s", testInput.getKey());
            assertEquals(assertMsg, expectedResult, actualResult);
        }
    }

}
