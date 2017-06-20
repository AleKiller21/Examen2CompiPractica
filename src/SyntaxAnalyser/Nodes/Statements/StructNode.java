package SyntaxAnalyser.Nodes.Statements;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.AttributeNodes.FieldNode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.TypeNodes.StructType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;
import SyntaxAnalyser.Nodes.TypesTable;

import java.util.ArrayList;
import java.util.HashMap;

public class StructNode extends StatementNode {
    public String identifier;
    public ArrayList<DeclarationStatement> declarations;

    public StructNode(String id, ArrayList<DeclarationStatement> declarations) {
        identifier = id;
        this.declarations = declarations;
    }

    @Override
    public void validateSemantic() throws Exception {
        if(TypesTable.types.containsKey(identifier))
            throw new SemanticException(String.format("An struct with same identifier %s already exists.", identifier));

        StructType struc = new StructType();
        struc.fields = initializeStructFields();
        TypesTable.types.put(identifier, struc);
    }

    private HashMap<String, TypeNode> initializeStructFields() throws Exception {
        HashMap<String, TypeNode> fields = new HashMap<>();
        for(DeclarationStatement decl : declarations) {
            fields.put(decl.id.toString(), decl.type.evaluateType());
        }

        return fields;
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
