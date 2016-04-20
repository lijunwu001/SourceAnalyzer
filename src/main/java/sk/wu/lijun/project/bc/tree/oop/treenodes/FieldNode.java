package sk.wu.lijun.project.bc.tree.oop.treenodes;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Lijun on 2015-12-19.
 */
@Table(name="FIELD_NODE")
public class FieldNode extends AbstractModifiableNode {

    @ManyToOne(targetEntity = ClassNode.class, fetch = FetchType.EAGER)
    @Column(name = "CLASS_NODE_ID")
    ClassNode clazz;

    @Column(name = "DATA_TYPE")
    private String dataType;

    public FieldNode(String name) {
        super(name);
    }

    public void setClassNode(ClassNode classNode) {
        this.clazz = classNode;
    }

    public void setDataType(String type) {
        this.dataType = type;
    }

    public String getDataType() {
        return dataType;
    }
}
