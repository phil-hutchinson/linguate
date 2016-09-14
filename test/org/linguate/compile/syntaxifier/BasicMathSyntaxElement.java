/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxElement implements SyntaxElement
{
    private String name;

    public BasicMathSyntaxElement(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
    
}
