/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import java.util.List;
import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 * A RegexNode represents an element within a Regex tree. A node can be a leaf node
 * or one of three types of inner nodes; or node, cat node, and star node.
 * 
 * <p>A Leaf node represents a character or character class within a Regex. In a typical
 * Regex such as  [a-z]f*, the character class [a-z] would be represented by a leaf 
 * node, as would the character f. A leaf node cannot have any children.
 * 
 * <p>Alternatively, a Leaf node is also used for accepting a terminal. A terminal accepting
 * leaf node is a placeholder that does not accept any characters or characters classes
 * (or alternatively, it can be thought of as accepting the empty string.) For a simple
 * Regex that accepts a single token, a cat node should combine the rest of the Regex
 * with the accept node. This cat node also becomes the root node of the Regex tree.
 * 
 * <p>An Or Node represents an alternative between two or more different possibilities.
 * In a Regex such as a|b, The a and b would be leaf nodes, with both of these leaf nodes
 * being children of an or node.
 * 
 * <p><i>Note: In the case of a language description that includes many Regex-defined
 * tokens, all of these individual token definitions can be combined under an or node,
 * and this Or Node can then be used a the root of a Regex tree can then be passed to a</i>
 * 
 * <p>A Cat Node represents a sequence of nodes, and contains exactly two children, with
 * the second child following the first in the RegEx. As an example, The Regex of
 * abc would have a cat node joining a and b (each represented by leaf nodes), and a
 * second cat node joining the first cat ndoe and a leaf node for c.
 * 
 * <p>A Star Node represents a section of a Regex that can be excluded, or else included
 * one or more times. It always has exactly one child. In the Regex (abc|d)*, the entire
 * sequence (abc|d) will be the child of the star node, indicating that the entire
 * sequence can be included 0 or more times within the Regex.
 * 
 * @author Phil Hutchinson
 */
public interface RegexNode {

    /**
     * One of four node types. Full documentation on the node types is included
     * in the documentation for the {@link org.linguate.compile.lexergenerator.RegexNode
     * class}.
     */
    public enum NodeType
    {
        LEAF,
        OR_NODE,
        CAT_NODE,
        STAR_NODE,
    }
    
    /**
     * Gets the NodeType of the node
     * @return the type of the node
     */
    NodeType getNodeType();
    
    /**
     * Gets a list of all of the children of the node. For a leaf node, this list
     * will be empty or null.
     * 
     * @return the children of the node
     */
    List<RegexNode> getChildren();

    /**
     * Returns the set of characters accepted by the node.
     * 
     * <p>This method can only called for leaf nodes. A leaf node should either
     * return a set of characters from this method, or a terminal from {@link
     * #acceptsTerminal() acceptsTerminal), but not both.
     * 
     * @return characters accepted by leaf node
     */
    Set<Character> getCharacterSet();

    /**
     * Returns the grammar terminal accepted by the node, or null if it is not
     * an accepting node.
     * 
     * <p>This method can only called for leaf nodes. A leaf node should either
     * return a set of characters from this method, or a terminal from {@link
     * #getCharacterSet()  getCharacterSet), but not both.
     * 
     * @return the GrammarTerminal accepted by this node, or null if no grammar
     * terminal is accepted
     */
    GrammarTerminal acceptsTerminal();
}

