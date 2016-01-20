package sk.wu.lijun.project.bc.parser;

import org.antlr.v4.runtime.tree.Tree;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lijun on 2015-12-19.
 */
public interface Parser {
    Tree parse(InputStream inputStream) throws IOException;
}
