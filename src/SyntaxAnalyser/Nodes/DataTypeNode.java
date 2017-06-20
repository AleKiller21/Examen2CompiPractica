package SyntaxAnalyser.Nodes;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class DataTypeNode extends LineNumbering {
    private String id;

    public DataTypeNode(String id) {
        this.id = id;
    }

    public TypeNode evaluateType() throws Exception {
        if(TypesTable.types.containsKey(id))
            return TypesTable.types.get(id);
        else
            throw new SemanticException(String.format("Unknown type found at row %d column %d", row, col));
    }
}
