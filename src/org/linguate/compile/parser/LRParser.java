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
        LRParserDefinition.ActionType nextActionType = null;
        
        if (input == null)
        {
            return null;
        }
        
        Iterator<? extends Lexeme> inputIterator = input.iterator();
        if (!inputIterator.hasNext())
        {
            return null;
        }
        
        LRParserStackState initialState = new LRParserStackState(LRParserDefinition.START_STATE, null);
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
            int stackTop = parseStack.peek().state;
            
            if (nextNode == null && inputIterator.hasNext())
            {
                nextNode = nodeFactory.generateNodeForLexeme(inputIterator.next());
            }
            GrammarTerminal nextTerminal;
            
            if (nextNode == null)
            {
                nextTerminal = null;
            }
            else
            { 
                nextTerminal = (GrammarTerminal) nextNode.getElement();
            }
            
            nextActionType = definition.getActionType(stackTop, nextTerminal);
            
            switch (nextActionType)
            {
                case Reject:
                    throw new ParserException("Input Rejected"); // ENHANCEMENT: better error handling capabilities
                    
                case Accept:
                {
                    stillProcessing = false;
                    result = parseStack.pop().node;
                }
                break;
                
                case Shift:
                {
                    int shiftState = definition.getShiftState(stackTop, nextTerminal);
                    LRParserStackState newShiftState = new LRParserStackState(shiftState, nextNode);
                    parseStack.push(newShiftState);
                    nextNode = null;
                }
                break;
                
                case Reduce:
                {
                    GrammarProduction rule = definition.getReduceRule(stackTop, nextTerminal);
                    int childrenCount = rule.getBodyLength();
                    List<DFTNode> children = new ArrayList<>(childrenCount);
                    for (int indexPos = 0; indexPos < childrenCount; indexPos++)
                    {
                        LRParserStackState poppedItem = parseStack.pop();
                        children.add(poppedItem.node);
                    }
                    Collections.reverse(children);
                    DFTNode productionNode = nodeFactory.generateNodeForProduction(rule, children);
                    int topState = parseStack.peek().state;
                    int postReduceState = definition.getPostReductionState(topState, rule.getHead());
                    if (postReduceState == LRParserDefinition.NO_POST_REDUCTION_STATE)
                    {
                        throw new ParserException("Parser Error (missing post reduce state)");
                    }
                    LRParserStackState newReduceState = new LRParserStackState(postReduceState, productionNode);
                    parseStack.push(newReduceState);
                }
                break;
            }
                
        }
     
        return result;
    }
}
