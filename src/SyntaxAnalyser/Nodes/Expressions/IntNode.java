package SyntaxAnalyser.Nodes.Expressions;


import SyntaxAnalyser.Nodes.TypeNodes.IntType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class IntNode extends ExpressionNode {
    private int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public TypeNode evaluateType() {
        return new IntType();
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
