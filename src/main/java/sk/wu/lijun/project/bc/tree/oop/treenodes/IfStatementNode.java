package sk.wu.lijun.project.bc.tree.oop.treenodes;

import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Lijun on 2015-12-19.
 */
@Table(name = "IF_STATEMENT_NODE")
public class IfStatementNode extends StatementNode {
    private static final String NAME = "if";
    @OneToOne(mappedBy = "expression")
    private ExpressionNode expression;

    public IfStatementNode() {
        super(NAME);
    }
}
