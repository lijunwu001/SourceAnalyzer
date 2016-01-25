package sk.wu.lijun.project.bc.tree.oop;

import sk.wu.lijun.project.bc.enums.NodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class ClassNode extends AbstractModifiableNode {
    private ClassNode extendsClass;
    private List<InterfaceNode> ifaces;
    private List<FieldNode> fields;
    public ClassNode(String name) {
        super(name);
    }

    public void setParent(ClassNode parent) {
        this.extendsClass = parent;
        ifaces = new ArrayList<InterfaceNode>();
    }

    @Override
    public NodeType getType() {
        return NodeType.CLASS;
    }

    public ClassNode getParent() {
        return extendsClass;
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
}
