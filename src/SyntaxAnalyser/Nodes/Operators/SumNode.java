package SyntaxAnalyser.Nodes.Operators;


import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.TypeNodes.BoolType;
import SyntaxAnalyser.Nodes.TypeNodes.IntType;

import java.util.HashMap;

public class SumNode extends BinaryOperator {

    public SumNode(ExpressionNode leftOperand, ExpressionNode rightOperand) {
        super(leftOperand, rightOperand);
        this.rules = new HashMap<>();
        this.rules.put("int,int", new IntType());
        this.rules.put("bool,bool", new BoolType());
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
