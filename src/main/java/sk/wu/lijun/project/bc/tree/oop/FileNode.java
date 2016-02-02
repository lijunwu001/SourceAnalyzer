package sk.wu.lijun.project.bc.tree.oop;

import sk.wu.lijun.project.bc.enums.NodeType;
import sk.wu.lijun.project.bc.tree.oop.treeHierarchy.TreeNode;

/**
 * Created by Lijun on 2016-01-21.
 */
public class FileNode extends TreeNode {
    String filePath;

    @Override
    public NodeType getType() {
        return NodeType.UNKNOWN;
    }
}
