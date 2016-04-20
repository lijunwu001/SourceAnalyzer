package sk.wu.lijun.project.bc.tree.oop.treenodes;

import sk.wu.lijun.project.bc.enums.NodeType;

/**
 * Created by Lijun on 2016-01-21.
 */
public class FileNode extends AbstractTreeNode {
    private String filePath;
    public FileNode(String filePath){
        super(null);
        this.filePath = filePath;
    }

    @Override
    public NodeType getType() {
        return NodeType.UNKNOWN;
    }
}
