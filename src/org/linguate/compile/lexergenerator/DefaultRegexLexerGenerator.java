/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexer.DFALexerDefinition;
import org.linguate.compile.lexergenerator.RegexNode.NodeType;

/**
 *
 * @author phil
 */
public class DefaultRegexLexerGenerator implements RegexLexerGenerator
{
    private Map<RegexNode, Boolean> nullableNodes = new HashMap<RegexNode, Boolean>();
    private Map<RegexNode, Set<RegexNode>> firstPosNodes = new HashMap<RegexNode, Set<RegexNode>>();
    private Map<RegexNode, Set<RegexNode>> lastPosNodes = new HashMap<RegexNode, Set<RegexNode>>();
    private Map<RegexNode, Set<RegexNode>> followPosNodes = new HashMap<RegexNode, Set<RegexNode>>();
    
    private RegexNode rootNode;
    private TerminalPrioritizer terminalPrioritizer;
    
    @Override
    public DFALexerDefinition generate(RegexNode rootNode, TerminalPrioritizer terminalPrioritizer, DFALexerDefinitionBuilder builder) {
        if (rootNode == null) {
            throw new NullPointerException("Root node cannot be set to null.");
        }
        if (terminalPrioritizer == null) {
            throw new NullPointerException("Terminal prioritizer cannot be set to null.");
        }
        
        this.rootNode = rootNode;
        this.terminalPrioritizer = terminalPrioritizer;
        
        processDepthFirst(rootNode, this::calculateNullable);
        processDepthFirst(rootNode, this::calculateFirstPos);
        processDepthFirst(rootNode, this::calculateLastPos);
        processDepthFirst(rootNode, this::calculateFollowPos);
        return buildStatesAndEdges(builder);
    }

    private void processDepthFirst(RegexNode node, Consumer<? super RegexNode> action) {
        List<RegexNode> children = node.getChildren();
        if (children != null) {
            children.forEach(n -> processDepthFirst(n, action));
        }
        action.accept(node);
    }

    private void calculateNullable(RegexNode node) {
        boolean nullable;
        NodeType nodeType = node.getNodeType();
        List<RegexNode> children;
        switch(nodeType) {
            case LEAF:
                Set<Character> characters = node.getCharacterSet();
                GrammarTerminal terminalAccepted = node.acceptsTerminal();
                nullable = (terminalAccepted == null) && (characters == null || characters.isEmpty());
            break;
            
            case STAR_NODE:
                nullable = true;
            break;
            
            case CAT_NODE:
                children = node.getChildren();
                boolean leftChildNullable = nullableNodes.get(children.get(0));
                boolean rightChildNullable = nullableNodes.get(children.get(1));
                nullable = leftChildNullable && rightChildNullable;
            break;

            case OR_NODE:
                children = node.getChildren();
                int childPos = 0;
                nullable = false;
                while (!nullable && childPos < children.size()) {
                    boolean currNullable = nullableNodes.get(children.get(childPos));
                    nullable = nullable || currNullable;
                    childPos++;
                }
            break;
            
            default:
                throw new IllegalStateException("Node Type is not set to a legal value.");
        }
        
        nullableNodes.put(node, nullable);
    }

    private void calculateFirstPos(RegexNode node) {
        HashSet<RegexNode> firstPos = new HashSet<RegexNode>();
        NodeType nodeType = node.getNodeType();
        List<RegexNode> children;
        switch(nodeType)
        {
            case LEAF:
                boolean isNullable = nullableNodes.get(node);
                if (!isNullable) {
                    firstPos.add(node);
                }
            break;
            
            case STAR_NODE:
                RegexNode child = node.getChildren().get(0);
                firstPos.addAll(firstPosNodes.get(child));
            break;
            
            case CAT_NODE:
                children = node.getChildren();
                RegexNode leftChild = children.get(0);
                Set<RegexNode> leftChildNodes = firstPosNodes.get(leftChild);
                firstPos.addAll(leftChildNodes);
                boolean leftChildNullable = nullableNodes.get(leftChild);
                if (leftChildNullable) {
                    RegexNode rightChild = children.get(1);
                    Set<RegexNode> rightChildNodes = firstPosNodes.get(rightChild);
                    firstPos.addAll(rightChildNodes);
                }
            break;

            case OR_NODE:
                children = node.getChildren();
                int childPos = 0;
                while (childPos < children.size()) {
                    Set<RegexNode> currNodes = firstPosNodes.get(children.get(childPos));
                    firstPos.addAll(currNodes);
                    childPos++;
                }
            break;
            
            default:
                throw new IllegalStateException("Node Type is not set to a legal value.");
        }
        
        firstPosNodes.put(node, firstPos);
    }

    private void calculateLastPos(RegexNode node) {
        HashSet<RegexNode> lastPos = new HashSet<RegexNode>();
        NodeType nodeType = node.getNodeType();
        List<RegexNode> children;
        switch(nodeType) {
            case LEAF:
                boolean isNullable = nullableNodes.get(node);
                if (!isNullable) {
                    lastPos.add(node);
                }
            break;
            
            case STAR_NODE:
                RegexNode child = node.getChildren().get(0);
                lastPos.addAll(lastPosNodes.get(child));
            break;
            
            case CAT_NODE:
                children = node.getChildren();
                RegexNode rightChild = children.get(1);
                Set<RegexNode> rightChildNodes = lastPosNodes.get(rightChild);
                lastPos.addAll(rightChildNodes);
                boolean rightChildNullable = nullableNodes.get(rightChild);
                if (rightChildNullable) {
                    RegexNode leftChild = children.get(0);
                    Set<RegexNode> leftChildNodes = firstPosNodes.get(leftChild);
                    lastPos.addAll(leftChildNodes);
                }
            break;

            case OR_NODE:
                children = node.getChildren();
                int childPos = 0;
                while (childPos < children.size()) {
                    Set<RegexNode> currNodes = lastPosNodes.get(children.get(childPos));
                    lastPos.addAll(currNodes);
                    childPos++;
                }
            break;
            
            default:
                throw new IllegalStateException("Node Type is not set to a legal value.");
        }
        
        lastPosNodes.put(node, lastPos);
    }

    private void calculateFollowPos(RegexNode node) {
        NodeType nodeType = node.getNodeType();
        switch(nodeType) {
            case LEAF:
            case OR_NODE:
                // do nothing
            break;
            
            case STAR_NODE:
                Set<RegexNode> firstPos = firstPosNodes.get(node);
                Set<RegexNode> lastPos = lastPosNodes.get(node);
                lastPos.forEach(n -> addFollowRange(n, firstPos));
            break;
            
            case CAT_NODE:
                RegexNode leftChild = node.getChildren().get(0);
                Set<RegexNode> leftLastPos = lastPosNodes.get(leftChild);
                RegexNode rightChild = node.getChildren().get(1);
                Set<RegexNode> rightFirstPos = firstPosNodes.get(rightChild);
                leftLastPos.forEach(n -> addFollowRange(n, rightFirstPos));
            break;

            default:
                throw new IllegalStateException("Node Type is not set to a legal value.");
        }
    }
    
    private void addFollowRange(RegexNode node, Set<RegexNode> followNodes) {
        Set<RegexNode> followPos;
        if (followPosNodes.containsKey(node)) {
            followPos = followPosNodes.get(node);
        } else {
            followPos = new HashSet<>();
            followPosNodes.put(node,followPos);
        }
        
        followPos.addAll(followNodes);
    }

    private DFALexerDefinition buildStatesAndEdges(DFALexerDefinitionBuilder builder) {
        Queue<Integer> statesToProcess = new ArrayDeque<>();
        Map<Integer, Set<RegexNode>> dfaStates = new HashMap<>();
        Map<Set<RegexNode>, Integer> dfaStateLookup = new HashMap<>();
        int nextState = 0;
        
        Set<RegexNode> startState = firstPosNodes.get(rootNode);
        dfaStates.put(nextState, startState);
        dfaStateLookup.put(startState, nextState);
        statesToProcess.add(nextState);
        nextState++;
        
        while (!statesToProcess.isEmpty()) {
            // processingStateNumber: state number in resultant DFA.
            int processingStateNumber = statesToProcess.remove();
            // processingState: corresponds to state in resulant DFA. Each RegexNode in this state represents
            // a leaf in the Regex Tree that is valid given the input that lead to this DFA state.
            Set<RegexNode> processingState = dfaStates.get(processingStateNumber);
            // rawTransitions: calculated for each processingState, and maps characters to all possible follow 
            // positions for te given character and give processingState. Each of the mapped sets of RegexNode's
            // will be a state in the resultant DFA, and each mapped item in rawTransitions (in combination
            // with the current processingState).
            Map<Character, Set<RegexNode>> rawTransitions = new HashMap<>();
            
            // For each character that is accepted as an input by at least one of the regex nodes in 
            // the DFA state being processed, determine all of the nodes in the previously
            // calcuated merged follow positions that can be reached for the given character.
            processingState.forEach(startNode -> {
                Set<Character> startNodeChars = startNode.getCharacterSet();
                if (startNodeChars != null) {
                    startNode.getCharacterSet().forEach(ch -> {
                        Set<RegexNode> toNodesForChar;
                        if (rawTransitions.containsKey(ch)) {
                            toNodesForChar = rawTransitions.get(ch);
                        } else {
                            toNodesForChar = new HashSet<>();
                            rawTransitions.put(ch, toNodesForChar);
                        }
                        Set<RegexNode> followPositions = followPosNodes.get(startNode);
                        toNodesForChar.addAll(followPositions);
                    });
                }
            });

            // For each transition identified in the previous step, there is a input character
            // and a set of regex nodes reachable using this character. Determine if this set
            // of regex nodes is already represented by a DFA state: if it is not, create a DFA
            // state and add it to the list of DFA states requiring processing. Also add the
            // transition to the DFA.
            for (Entry<Character, Set<RegexNode>> entry : rawTransitions.entrySet()) {
                char ch = entry.getKey();
                Set<RegexNode> toNodesForChar = entry.getValue();
                
                int toStateNumber;
                if (dfaStateLookup.containsKey(toNodesForChar)) {
                    toStateNumber = dfaStateLookup.get(toNodesForChar);
                } else {
                    toStateNumber = nextState;
                    dfaStates.put(toStateNumber, toNodesForChar);
                    dfaStateLookup.put(toNodesForChar, toStateNumber);
                    statesToProcess.add(toStateNumber);
                    nextState++;
                }
                builder.addTransition(processingStateNumber, ch, toStateNumber);
            }
            
            // determine if the DFA state currently being processed is an accepting
            // state, calling the helper interface where multiple terminals could
            // be accepted.
            Set<GrammarTerminal> acceptSet = processingState.stream()
                    .filter(n -> n.acceptsTerminal() != null)
                    .map(n -> n.acceptsTerminal())
                    .collect(Collectors.toSet());
            if (acceptSet.size() == 1) {
                builder.addAccept(processingStateNumber, acceptSet.iterator().next());
            }
            if (acceptSet.size() > 1) {
                GrammarTerminal highestPriority = terminalPrioritizer.selectHighestPriority(acceptSet);
                builder.addAccept(processingStateNumber, highestPriority);
            }
        }
        
        return builder.getDefinition();
    }
}
