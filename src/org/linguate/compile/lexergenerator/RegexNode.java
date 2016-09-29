/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.List;
import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author phil
 */
public interface RegexNode {
    public enum NodeType
    {
        LEAF,
        OR_NODE,
        CAT_NODE,
        STAR_NODE,
    }
    
    NodeType getNodeType();
    
    List<RegexNode> getChildren();
    Set<Character> getCharacterSet();
    GrammarTerminal acceptsTerminal();
}

