package sk.wu.lijun.project.bc.tree.oop;

import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class MethodNode extends AbstractModifiableNode {
    private List<String> formalParameters;
    List<StatementNode> statements;
    public MethodNode(String name) {
        super(name);
    }
}
