package SyntaxAnalyser.Nodes.AttributeNodes;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.TypeNodes.ArrayType;
import SyntaxAnalyser.Nodes.TypeNodes.IntType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class IndexArrayNode extends Attributes {
    private ExpressionNode index;

    public IndexArrayNode(ExpressionNode index) {
        this.index = index;
    }

    @Override
    public TypeNode evaluateType(TypeNode type) throws Exception {
        if(!(type instanceof ArrayType))
            throw new SemanticException(String.format("Variable %s at row %d column %d must be of array type",
                    row, col));

        TypeNode indexType = index.evaluateType();
        if(!(indexType instanceof IntType))
            throw new SemanticException(String.format("Index at row %d column %d must be of type int.", row, col));

        return ((ArrayType)type).type;
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
