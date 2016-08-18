/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.compile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.linguate.compile.LRParserAction.ActionType;
/**
 *
 * @author Phil Hutchinson
 */
public class LRParser
{
    private final LRParserDefinition definition;
    private final ParseNodeFactory nodeFactory;
    
    public LRParser(LRParserDefinition definition, ParseNodeFactory nodeFactory)
    {
        this.definition = definition;
        this.nodeFactory = nodeFactory;
    }
    
    public ParseNode Parse(Iterable<? extends Token> input) throws ParserException
    {
        ParseNode result = null;
        boolean stillProcessing = true;
        Stack<LRParserStackState> parseStack = new Stack<>();
        ParseNode nextNode = null;
        LRParserAction nextAction = null;
        
        if (input == null)
        {
            return null;
        }
        
        Iterator<? extends Token> inputIterator = input.iterator();
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
                    int childrenCount = rule.getBody().size();
                    List<ParseNode> children = new ArrayList<>(childrenCount);
                    for (int indexPos = 0; indexPos < childrenCount; indexPos++)
                    {
                        LRParserStackState poppedItem = parseStack.pop();
                        children.add(poppedItem.node);
                    }
                    Collections.reverse(children);
                    ParseNode productionNode = nodeFactory.CreateInnerNode(rule, children);
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
