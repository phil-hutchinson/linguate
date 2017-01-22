/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parsergenerator;

import java.util.Arrays;
import java.util.List;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarSymbol;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author Phil Hutchinson
 */
public class DefaultLRParserDefinitionBuilderWrappers {
    public static class GrammarTerminalWrapper implements GrammarTerminal {
        private String name;

        public GrammarTerminalWrapper(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
    
    public static class GrammarNonTerminalWrapper implements GrammarNonTerminal {
        private String name;

        public GrammarNonTerminalWrapper(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
    
    public static class GrammarProductionWrapper implements GrammarProduction {
        private GrammarNonTerminal head;
        private List<GrammarSymbol> body;

        public GrammarProductionWrapper(GrammarNonTerminal head, GrammarSymbol... body) {
            this.head = head;
            this.body = Arrays.asList(body);
        }
        
        @Override
        public GrammarNonTerminal getHead() {
            return head;
        }

        @Override
        public int getBodyLength() {
            return body.size();
        }

        @Override
        public List<GrammarSymbol> getBody() {
            return body;
        }
        
    }
    
}
