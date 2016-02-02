package sk.wu.lijun.project.bc.converter.cpp;

import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.converter.AbstractTreeConverter;
import sk.wu.lijun.project.bc.tree.oop.treenodes.AbstractTreeNode;

/**
 * Created by Lijun on 2015-12-19.
 */
public class CppTreeConverter implements AbstractTreeConverter {
    @Override
    public AbstractTreeNode convert(Tree tree) {
        for (int i=0;i<tree.getChildCount();i++){
            Tree child = tree.getChild(i);
            Object payload = child.getPayload();
            System.out.println(payload);
        }
        return null;
    }
}
