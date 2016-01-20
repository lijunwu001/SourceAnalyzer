package sk.wu.lijun.project.bc.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.parser.antlr.cpp.CPP14Lexer;
import sk.wu.lijun.project.bc.parser.antlr.cpp.CPP14Parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lijun on 2015-12-19.
 */
public class CppTreeParser implements Parser {
    private static CppTreeParser instance;

    @Override
    public Tree parse(InputStream inputStream) throws IOException {
        ANTLRInputStream cppInput = new ANTLRInputStream(inputStream);
        CPP14Lexer lexercpp = new CPP14Lexer(cppInput);
        CommonTokenStream cppTokens = new CommonTokenStream(lexercpp);
        CPP14Parser parser = new CPP14Parser(cppTokens);
        CPP14Parser.ExpressionContext cppTree = parser.expression();
        return cppTree;
    }

    public static Parser getInstance() {
        if (instance == null){
            instance = new CppTreeParser();
        }
        return instance;
    }
}
