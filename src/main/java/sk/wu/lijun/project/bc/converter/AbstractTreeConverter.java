package sk.wu.lijun.project.bc.converter;

import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.converter.exceptions.UnsupportNodeException;
import sk.wu.lijun.project.bc.tree.oop.AbstractTreeNode;

/**
 * Created by Lijun on 2015-12-19.
 */
public interface AbstractTreeConverter {
    AbstractTreeNode convert(Tree tree) throws UnsupportNodeException;
}
