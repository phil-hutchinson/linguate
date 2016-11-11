/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;
import static org.linguate.compile.lexergenerator.RegexNode.NodeType.*;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultRegexLexerGeneratorWrappers {
    public static class GrammarTerminalWrapper implements GrammarTerminal
    {
        String name;

        public GrammarTerminalWrapper(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
    }
    
    public static class RegexNodeWrapper implements RegexNode
    {
        private NodeType nodeType;
        private List<RegexNode> children;
        private Set<Character> characterSet;
        private GrammarTerminal accepts;

        private RegexNodeWrapper(NodeType nodeType, GrammarTerminal accepts, String characterSet, RegexNodeWrapper... children)
        {
            this.nodeType = nodeType;
            this.accepts = accepts;
            if (characterSet == null || characterSet.length() == 0)
            {
                this.characterSet = null;
            }
            else
            {
                this.characterSet = new HashSet<Character>();
                for (int charPos = 0; charPos < characterSet.length(); charPos++)
                {
                    this.characterSet.add(characterSet.charAt(charPos));
                }
            }
            
            this.children = new ArrayList<RegexNode>();
            if (children != null)
            {
                this.children.addAll(Arrays.asList(children));
            }
        }
        
        public static RegexNodeWrapper CreateRegularLeaf(String characterSet)
        {
            return new RegexNodeWrapper(LEAF, null, characterSet, null);
        }
        
        public static RegexNodeWrapper CreateAcceptLeaf(GrammarTerminal accepts)
        {
            return new RegexNodeWrapper(LEAF, accepts, "", null);
        }
        
        public static RegexNodeWrapper CreateCatNode(RegexNodeWrapper leftChild, RegexNodeWrapper rightChild)
        {
            if (leftChild == null || rightChild == null)
            {
                throw new RuntimeException("null children not allowed");
            }
            return new RegexNodeWrapper(CAT_NODE, null, "", leftChild, rightChild);
        }
                
        public static RegexNodeWrapper CreateStarNode(RegexNodeWrapper child)
        {
            if (child == null)
            {
                throw new RuntimeException("null children not allowed");
            }
            return new RegexNodeWrapper(STAR_NODE, null, "", child);
        }
                
        public static RegexNodeWrapper CreateOrNode(RegexNodeWrapper... children )
        {
           if (children.length < 2) 
           {
               throw new RuntimeException("or node requires at least two children");
           }
           return new RegexNodeWrapper(OR_NODE, null, "", children);
        }
        
        @Override
        public NodeType getNodeType() {
            return nodeType;
        }

        @Override
        public List<RegexNode> getChildren() {
            return children;
        }

        @Override
        public Set<Character> getCharacterSet() {
            return characterSet;
        }

        @Override
        public GrammarTerminal acceptsTerminal() {
            return accepts;
        }
    }
    
    public static class TerminalPrioritizerWrapper implements TerminalPrioritizer
    {
        List<GrammarTerminal> terminalPriority;

        public TerminalPrioritizerWrapper(List<GrammarTerminal> terminalPriority) {
            this.terminalPriority = terminalPriority;
        }
        
        public TerminalPrioritizerWrapper(GrammarTerminal... terminalPriority) {
            this.terminalPriority = new ArrayList<GrammarTerminal>();
            this.terminalPriority.addAll(Arrays.asList(terminalPriority));
        }
        
        @Override
        public GrammarTerminal selectHighestPriority(Set<GrammarTerminal> terminals) {
            int lowestFound = terminalPriority.size();
            GrammarTerminal terminalFound = null;
            for(GrammarTerminal terminal : terminals)
            {
                int currentPriority = terminalPriority.indexOf(terminal);
                if (currentPriority < lowestFound && currentPriority >= 0)
                {
                    lowestFound = currentPriority;
                    terminalFound = terminal;
                }
            }
            return terminalFound;
        }
        
    }
    
}
