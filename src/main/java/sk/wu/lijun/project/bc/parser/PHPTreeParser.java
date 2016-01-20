package sk.wu.lijun.project.bc.parser;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.SyntaxTree;
import org.antlr.v4.runtime.tree.Tree;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lijun on 2015-12-19.
 */
public class PHPTreeParser implements Parser {

    private static Parser instance;

    public static Parser getInstance() {
        if (instance == null){
            instance = new PHPTreeParser();
        }
        return instance;
    }

    @Override
    public Tree parse(InputStream inputStream) throws IOException {
        return new SyntaxTree() {
            @Override
            public Interval getSourceInterval() {
                return null;
            }

            @Override
            public Tree getParent() {
                return null;
            }

            @Override
            public Object getPayload() {
                return null;
            }

            @Override
            public Tree getChild(int i) {
                return null;
            }

            @Override
            public int getChildCount() {
                return 0;
            }

            @Override
            public String toStringTree() {
                return "NOT IMPLEMENTED PHP TREE";
            }
        };
    }
}
