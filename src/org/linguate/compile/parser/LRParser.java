/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.dft.DFTNodeFactory;
import org.linguate.compile.parser.LRParserAction.ActionType;
import org.linguate.compile.ParserException;
import org.linguate.compile.lexeme.Lexeme;
/**
 *
 * @author Phil Hutchinson
 */
public class LRParser
{
    private final LRParserDefinition definition;
    private final DFTNodeFactory nodeFactory;
    
    public LRParser(LRParserDefinition definition, DFTNodeFactory nodeFactory)
    {
        this.definition = definition;
        this.nodeFactory = nodeFactory;
    }
    
    public DFTNode Parse(Iterable<? extends Lexeme> input) throws ParserException
    {
        DFTNode result = null;
        boolean stillProcessing = true;
        Stack<LRParserStackState> parseStack = new Stack<>();
        DFTNode nextNode = null;
        LRParserAction nextAction = null;
        
        if (input == null)
        {
            return null;
        }
        
        Iterator<? extends Lexeme> inputIterator = input.iterator();
        if (!inputIterator.hasNext())
        {
            return null;
        }
        
        LRParserStackState initialState = new LRParserStackState((definition.getStartState()), null);
        parseStack.push(initialState);
        
        while (stillProcessing)
        {
            /* // output current stack contents:
            String currentStackContents = "";
            for(int stackIndex = 0; stackIndex < parseStack.size(); stackIndex++)
            {
                if (parseStack.get(stackIndex).node != null)
                {
                    currentStackContents += " " + parseStack.get(stackIndex).node.getElement().getName() + " ";
                }
                currentStackContents += parseStack.get(stackIndex).state.getName();
            }
            System.out.println(currentStackContents);
            */
            LRParserState stackTop = parseStack.peek().state;
            
            if (nextNode == null && inputIterator.hasNext())
            {
                nextNode = nodeFactory.CreateLeafNode(inputIterator.next());
            }
            
            if (nextNode == null)
            {
                nextAction = stackTop.getEndOfInputAction();
            }
            else
            { 
                GrammarTerminal nextTerminal = (GrammarTerminal) nextNode.getElement();
                nextAction = stackTop.getAction(nextTerminal);
            }
            
            if (nextAction == null)
            {
                throw new ParserException("Parse Error (missing action)"); // ENHANCEMENT: better error handling capabilities
            }
            
            switch (nextAction.getAction())
            {
                case Accept:
                    stillProcessing = false;
                    result = parseStack.pop().node;
                break;
                
                case Shift:
                    LRParserStackState newShiftState = new LRParserStackState(nextAction.getShiftState(), nextNode);
                    parseStack.push(newShiftState);
                    nextNode = null;
                break;
                
                case Reduce:
                    GrammarProduction rule = nextAction.getReduceRule();
                    int childrenCount = rule.getBodyLength();
                    List<DFTNode> children = new ArrayList<>(childrenCount);
                    for (int indexPos = 0; indexPos < childrenCount; indexPos++)
                    {
                        LRParserStackState poppedItem = parseStack.pop();
                        children.add(poppedItem.node);
                    }
                    Collections.reverse(children);
                    DFTNode productionNode = nodeFactory.CreateInnerNode(rule, children);
                    LRParserState postReduceState = parseStack.peek().state.getPostReductionState(rule.getHead());
                    LRParserStackState newReduceState = new LRParserStackState(postReduceState, productionNode);
                    if (postReduceState == null)
                    {
                        throw new ParserException("Parser Error (missing post reduce state)");
                    }
                    parseStack.push(newReduceState);
            }
                
        }
     
        return result;
    }
}
