package SyntaxAnalyser.Nodes.Statements;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.TypeNodes.BoolType;
import SyntaxAnalyser.Nodes.TypeNodes.IntType;

public class PrintNode extends StatementNode {
    public ExpressionNode value;

    public PrintNode(ExpressionNode value) {
        this.value = value;
    }

    @Override
    public void validateSemantic() throws Exception {
        if(!(value.evaluateType() instanceof IntType) && !(value.evaluateType() instanceof BoolType))
            throw new SemanticException(String.format("Invalid type found at row %d column %d", row, col));
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
