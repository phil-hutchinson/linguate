/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.compile;

import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public interface ParseNodeFactory
{
    ParseNode CreateInnerNode(GrammarProduction production, List<ParseNode> children);
    ParseNode CreateLeafNode(Token element);
}
