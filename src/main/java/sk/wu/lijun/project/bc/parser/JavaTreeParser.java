package sk.wu.lijun.project.bc.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaLexer;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lijun on 2015-12-19.
 */
public class JavaTreeParser implements Parser {
    private static JavaTreeParser instance;

    @Override
    public Tree parse(InputStream inputStream) throws IOException {
        ANTLRInputStream javaInput = new ANTLRInputStream(inputStream);
        JavaLexer javaLexer = new JavaLexer(javaInput);
        CommonTokenStream javaTokens = new CommonTokenStream(javaLexer);
        JavaParser javaParser = new JavaParser(javaTokens);
        RuleContext tree = javaParser.compilationUnit().getRuleContext();
        return tree;
    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new JavaTreeParser();
        }
        return instance;
    }
}
