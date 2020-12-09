/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import entityRelationshipModel.ERModel;
import grammar.ErGrammarLexer;
import grammar.ErGrammarParser;
import lombok.Getter;

/**
 *
 * @author Shanki, Adam Trizna
 */
public class Parser {
    
    private Parser() {
    }
    
    public static String fileToString(String path) throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    
    public static ERModel fromString(String string) throws SyntaxException {
        CharStream input = CharStreams.fromString(string);
        return fromCharStream(input);
    }

    public static ERModel fromFile(String path) throws IOException, SyntaxException {
        CharStream input = CharStreams.fromFileName(path);
        return fromCharStream(input);
    }

    public static ERModel fromReader(Reader reader) throws IOException, SyntaxException {
        CharStream input = CharStreams.fromReader(reader);
        return fromCharStream(input);
    }

    private static ERModel fromCharStream(CharStream input) throws SyntaxException {

        ErGrammarLexer lexer = new ErGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ErGrammarParser parser = new ErGrammarParser(tokens);
        BaseErrorListenerImpl errorListener = new BaseErrorListenerImpl();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.model();
        if (errorListener.hasErrors()) {
            throw new SyntaxException(errorListener.errors);
        }

        ErGrammarVisitor visitor = new ErGrammarVisitor();
        return visitor.transform(tree);
    }

    private static class BaseErrorListenerImpl extends BaseErrorListener {

        @Getter
        private final List<String> errors = new ArrayList<>();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            List<String> stack = ((org.antlr.v4.runtime.Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);

            StringBuilder error = new StringBuilder();
            error.append("rule stack: ").append(stack).append(System.lineSeparator());
            error.append("line ").append(line).append(":").append(charPositionInLine).append(" at ").append(offendingSymbol).append(": ").append(msg);

            errors.add(error.toString());
        }

        private boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
    
}
