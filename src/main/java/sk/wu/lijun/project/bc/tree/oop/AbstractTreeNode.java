package sk.wu.lijun.project.bc.tree.oop;

import sk.wu.lijun.project.bc.enums.NodeType;
import sk.wu.lijun.project.bc.tree.oop.treeHierarchy.ITreeNode;
import sk.wu.lijun.project.bc.tree.oop.treeHierarchy.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class AbstractTreeNode extends TreeNode {
    private String name;
    private List<ITreeNode> children;
    public AbstractTreeNode parent;

    public NodeType getType(){
        return NodeType.UNKNOWN;
    }

    public AbstractTreeNode(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


}
