package sk.wu.lijun.project.bc.tree.oop.treenodes;

import sk.wu.lijun.project.bc.enums.NodeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
@Table(name = "CLASS_NODE")
public class ClassNode extends AbstractModifiableNode {

    @OneToOne(mappedBy = "extendsClass")
    @Column(name="EXTEND_CLASS")
    private ClassNode extendsClass;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classNode")
    private List<InterfaceNode> ifaces;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classNode")
    private List<FieldNode> fields;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classNode")
    private List<MethodNode> methods;

    public ClassNode(String name) {
        super(name);
        ifaces = new ArrayList<>();
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    @Override
    public NodeType getType() {
        return NodeType.CLASS;
    }

    public List<InterfaceNode> getIfaces() {
        return ifaces;
    }

    public void setIfaces(List<InterfaceNode> ifaces) {
        this.ifaces = ifaces;
    }
    public void addIface(InterfaceNode iface){
        this.ifaces.add(iface);
    }

    public void addField(FieldNode fieldNode){
        this.fields.add(fieldNode);
    }

    public void addMethod(MethodNode method) {
        this.methods.add(method);
    }
}
