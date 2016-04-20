package sk.wu.lijun.project.bc.tree.oop.treenodes;

import org.hibernate.annotations.GenericGenerator;
import sk.wu.lijun.project.bc.enums.NodeType;
import sk.wu.lijun.project.bc.tree.oop.treeHierarchy.ITreeNode;
import sk.wu.lijun.project.bc.tree.oop.treeHierarchy.TreeNode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class AbstractTreeNode extends TreeNode implements IPersistenceObject, Serializable {

    @Column(name = "NAME")
    private String name;
    private List<ITreeNode> children;
    @OneToOne
    public AbstractTreeNode parent;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(long id) {

    }

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
