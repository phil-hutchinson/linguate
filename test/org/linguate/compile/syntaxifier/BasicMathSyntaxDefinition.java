/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.linguate.compile.grammar.GrammarProduction;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxDefinition implements SyntaxDefinition
{
    public final static BasicMathGrammarTerminal NUMERIC_LITERAL = new BasicMathGrammarTerminal("numeric");
    public final static BasicMathGrammarTerminal ADDITION_OPERATOR = new BasicMathGrammarTerminal("+");
    public final static BasicMathGrammarTerminal MULTIPLICATION_OPERATOR = new BasicMathGrammarTerminal("*");
    public final static BasicMathGrammarTerminal LEFT_BRACKET = new BasicMathGrammarTerminal("(");
    public final static BasicMathGrammarTerminal RIGHT_BRACKET = new BasicMathGrammarTerminal(")");
    
    public final static BasicMathGrammarProduction REDUCE_EAT_TO_E = new BasicMathGrammarProduction("Expression, Addition, Term TO Expression");
    public final static BasicMathGrammarProduction REDUCE_T_TO_E = new BasicMathGrammarProduction("Term TO Expression");
    public final static BasicMathGrammarProduction REDUCE_TMF_TO_T = new BasicMathGrammarProduction("Term, Multiplion, Factor TO Factor");
    public final static BasicMathGrammarProduction REDUCE_F_TO_T = new BasicMathGrammarProduction("Factor TO Term");
    public final static BasicMathGrammarProduction REDUCE_LER_TO_F = new BasicMathGrammarProduction("Left Bracket, Expression, Right Bracket TO Factor");
    public final static BasicMathGrammarProduction REDUCE_N_TO_F = new BasicMathGrammarProduction("Numeric Literal TO Factor");
    
    private static Map<GrammarProduction, SyntaxRule> unmodifiableRuleMap;
    
    static 
    {
        HashMap<GrammarProduction, SyntaxRule> ruleMap = new HashMap<>();
        BasicMathSyntaxRule RULE_EAT_TO_E = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(1),
                new BasicMathSyntaxRuleComponent(0),
                new BasicMathSyntaxRuleComponent(2)
        );
        BasicMathSyntaxRule RULE_T_TO_E = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(0)
        );
        BasicMathSyntaxRule RULE_TMF_TO_T = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(1),
                new BasicMathSyntaxRuleComponent(0),
                new BasicMathSyntaxRuleComponent(2)
        );
        BasicMathSyntaxRule RULE_F_TO_T = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(0)
        );
        BasicMathSyntaxRule RULE_LER_TO_F = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(1)
        );
        BasicMathSyntaxRule RULE_N_TO_F = new BasicMathSyntaxRule(
                new BasicMathSyntaxRuleComponent(0)
        );


        ruleMap.put(REDUCE_EAT_TO_E, RULE_EAT_TO_E);
        ruleMap.put(REDUCE_T_TO_E, RULE_T_TO_E);
        ruleMap.put(REDUCE_TMF_TO_T, RULE_TMF_TO_T);
        ruleMap.put(REDUCE_F_TO_T, RULE_F_TO_T);
        ruleMap.put(REDUCE_LER_TO_F, RULE_LER_TO_F);
        ruleMap.put(REDUCE_N_TO_F, RULE_N_TO_F);
        
        unmodifiableRuleMap = Collections.unmodifiableMap(ruleMap);
    }
    
    @Override
    public Map<GrammarProduction, SyntaxRule> getRuleMap()
    {
        return unmodifiableRuleMap;
    }
    
}
