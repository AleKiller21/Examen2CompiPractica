package SyntaxAnalyser.Nodes.Operators;


import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.TypeNodes.IntType;

import java.util.HashMap;

public class SubNode extends BinaryOperator {

    public SubNode(ExpressionNode leftOperand, ExpressionNode rightOperand) {
        super(leftOperand, rightOperand);
        this.rules = new HashMap<>();
        this.rules.put("int,int", new IntType());
    }
    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
