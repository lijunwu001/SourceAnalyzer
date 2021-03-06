package sk.wu.lijun.project.bc.tree.oop.treenodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class AbstractModifiableNode extends AbstractTreeNode {

    private List<String> modifiers;
    public AbstractModifiableNode(String name) {
        super(name);
        modifiers = new ArrayList<String>();
    }
    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<String> modifiers){
        this.modifiers = modifiers;
    }

    public void addModifier(String modifier){
        modifiers.add(modifier);
    }
}
