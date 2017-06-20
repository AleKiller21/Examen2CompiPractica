package SyntaxAnalyser.Nodes.Operators;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

import java.util.HashMap;

public abstract class BinaryOperator extends ExpressionNode {
    public ExpressionNode leftOperand;
    public ExpressionNode rightOperand;
    public HashMap<String, TypeNode> rules;

    public BinaryOperator(ExpressionNode leftOperand, ExpressionNode rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public TypeNode evaluateType() throws Exception {
        String typeOperands = leftOperand.evaluateType().toString() + "," + rightOperand.evaluateType().toString();
        if(!rules.containsKey(typeOperands))
            throw new SemanticException(String.format("Invalid type operands in expression at row %d column %d",
                    row, col));

        return rules.get(typeOperands);
    }
}
