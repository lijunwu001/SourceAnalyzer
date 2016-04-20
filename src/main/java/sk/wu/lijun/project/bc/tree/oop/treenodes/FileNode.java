package sk.wu.lijun.project.bc.tree.oop.treenodes;

import sk.wu.lijun.project.bc.enums.NodeType;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by Lijun on 2016-01-21.
 */
@Table(name = "FILE_NODE")
public class FileNode extends AbstractTreeNode {
    @Column(name = "FILE_PATH")
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
