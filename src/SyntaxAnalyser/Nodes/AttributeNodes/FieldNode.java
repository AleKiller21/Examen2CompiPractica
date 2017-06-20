package SyntaxAnalyser.Nodes.AttributeNodes;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.TypeNodes.StructType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class FieldNode extends Attributes {
    private String id;

    public FieldNode(String id) {
        this.id = id;
    }

    @Override
    public TypeNode evaluateType(TypeNode type) throws SemanticException{

        if(!(type instanceof StructType))
            throw new SemanticException(String.format("Variable %s at row %d column %d must be of struct type",
                    id, row, col));

        StructType struct = ((StructType) type);
        if(!struct.fields.containsKey(id))
            throw new SemanticException(String.format("%s identifier is not a struct field at row %d column %d",
                    id, row, col));

        return struct.fields.get(id);
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
