/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxRule implements SyntaxRule
{
    private BasicMathSyntaxRuleComponent parent;
    private ArrayList<BasicMathSyntaxRuleComponent> children = new ArrayList<>();

    public BasicMathSyntaxRule(BasicMathSyntaxRuleComponent parent, BasicMathSyntaxRuleComponent... children)
    {
        this.parent = parent;
        if (children != null)
        {
            this.children.addAll(Arrays.asList(children));
        }
    }
    
    @Override
    public BasicMathSyntaxRuleComponent getParentComponent()
    {
        return parent;
    }

    @Override
    public List<BasicMathSyntaxRuleComponent> getChildrenComponents()
    {
        return Collections.unmodifiableList(children);
    }
    
}
