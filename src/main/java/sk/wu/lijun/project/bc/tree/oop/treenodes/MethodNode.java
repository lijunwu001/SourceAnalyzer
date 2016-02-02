package sk.wu.lijun.project.bc.tree.oop.treenodes;

import sk.wu.lijun.project.bc.tree.oop.FormalParameter;

import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class MethodNode extends AbstractModifiableNode {
    private List<FormalParameter> formalParameters;
    List<StatementNode> statements;
    public MethodNode(String name) {
        super(name);
    }
}
