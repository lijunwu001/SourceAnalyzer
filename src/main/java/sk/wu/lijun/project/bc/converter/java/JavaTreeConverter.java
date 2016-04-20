package sk.wu.lijun.project.bc.converter.java;

import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.converter.AbstractTreeConverter;
import sk.wu.lijun.project.bc.converter.exceptions.UnsupportNodeException;
import sk.wu.lijun.project.bc.tree.oop.treenodes.AbstractTreeNode;

/**
 * Created by Lijun on 2015-12-19.
 */
public class JavaTreeConverter implements AbstractTreeConverter {
    @Override
    public AbstractTreeNode convert(Tree tree) throws UnsupportNodeException {
        if (tree == null) return null;
       JavaTreeProcessor processor = new JavaTreeProcessor(tree);
        processor.process();
        return processor.getInternalTree();
    }
}
