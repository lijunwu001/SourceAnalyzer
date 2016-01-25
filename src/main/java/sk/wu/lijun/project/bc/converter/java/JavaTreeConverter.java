package sk.wu.lijun.project.bc.converter.java;

import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.converter.AbstractTreeConverter;
import sk.wu.lijun.project.bc.converter.exceptions.UnsupportNodeException;
import sk.wu.lijun.project.bc.log.Logger;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;
import sk.wu.lijun.project.bc.tree.oop.AbstractTreeNode;

/**
 * Created by Lijun on 2015-12-19.
 */
public class JavaTreeConverter implements AbstractTreeConverter {
    @Override
    public AbstractTreeNode convert(Tree tree) throws UnsupportNodeException {
        if (tree == null) return null;
        Logger.getLogger().log("Convering "+tree.getClass().getSimpleName());
        AbstractTreeNode treeNode = null;
        if (tree instanceof JavaParser.CompilationUnitContext) {
            treeNode = JavaTreeConverterUtils.convertAntlrTreeNode(tree);
        }

        for (int i = 0; i < tree.getChildCount(); i++) {
            Tree child = tree.getChild(i);
            Object payload = child.getPayload();
            try {
                treeNode.addChild(JavaTreeConverterUtils.convertAntlrTreeNode(payload));
            } catch (UnsupportNodeException e) {
                Logger.getLogger().log(e.getMessage());
            }
        }
        return treeNode;
    }
}
