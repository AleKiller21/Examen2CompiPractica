package SyntaxAnalyser.Nodes.Expressions;

import SyntaxAnalyser.Nodes.TypeNodes.BoolType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class BoolNode extends ExpressionNode {
    private boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public TypeNode evaluateType() {
        return new BoolType();
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
