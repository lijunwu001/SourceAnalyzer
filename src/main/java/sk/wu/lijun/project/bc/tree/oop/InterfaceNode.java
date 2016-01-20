package sk.wu.lijun.project.bc.tree.oop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class InterfaceNode extends AbstractTreeNode {
    List<InterfaceNode> extendsIfaces;
    public InterfaceNode(String name) {
        super(name);
        extendsIfaces = new ArrayList<InterfaceNode>();
    }

    public void addExtendsIfaces(InterfaceNode iface) {
        this.extendsIfaces.add(iface);
    }
    public List<InterfaceNode> getExtendsIfaces(){
        return this.extendsIfaces;
    }
}
