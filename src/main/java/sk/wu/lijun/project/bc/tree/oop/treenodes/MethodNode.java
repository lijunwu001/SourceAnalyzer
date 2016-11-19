package sk.wu.lijun.project.bc.tree.oop.treenodes;

import sk.wu.lijun.project.bc.tree.oop.FormalParameter;

import java.util.List;

/**
 * Created by Lijun on 2015-12-19.
 */
public class MethodNode extends AbstractModifiableNode {
    private List<FormalParameter> formalParameters;
    private List<VariableNode> localVariable;
    private String returnType;
    private List<StatementNode> statements;
    public MethodNode(String name) {
        super(name);
    }

    public void setFormalParameters(List<FormalParameter> formalParameters) {
        this.formalParameters = formalParameters;
    }

    public List<FormalParameter> getFormalParameters() {
        return formalParameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
