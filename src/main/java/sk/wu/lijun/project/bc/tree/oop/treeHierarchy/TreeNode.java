package sk.wu.lijun.project.bc.tree.oop.treeHierarchy;

import sk.wu.lijun.project.bc.enums.NodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2016-01-21.
 */
public abstract class TreeNode implements ITreeNode {
    List<ITreeNode> children;
    ITreeNode parent;
    public abstract NodeType getType();
    public TreeNode(){
        children = new ArrayList<>();
    }

    @Override
    public List getChildren() {
        return children;
    }

    @Override
    public void addChild(ITreeNode node) {
        children.add(node);
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    @Override
    public ITreeNode getChild(int i) {
        return children.size() > i ? children.get(i) : null;
    }

}
