package sk.wu.lijun.project.bc.tree.oop.treeHierarchy;

import java.util.List;

/**
 * Created by Lijun on 2016-01-20.
 */
public interface ITreeNode {
    List getChildren();
    void addChild(ITreeNode node);
    ITreeNode getParent();
    ITreeNode getChild(int i);
}
