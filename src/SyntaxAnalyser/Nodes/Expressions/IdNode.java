package SyntaxAnalyser.Nodes.Expressions;


import SemanticExceptions.UndefinedVariableException;
import SyntaxAnalyser.Nodes.AttributeNodes.Attributes;
import SyntaxAnalyser.Nodes.SymbolsTable;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

import java.util.ArrayList;

public class IdNode extends ExpressionNode {
    private String lexeme;
    private ArrayList<Attributes> attributes;

    public IdNode(String id) {
        lexeme = id;
        attributes = new ArrayList<>();
    }

    public IdNode(String id, ArrayList<Attributes> attrs) {
        lexeme = id;
        attributes = attrs;
    }

    @Override
    public TypeNode evaluateType() throws Exception {
        if(!SymbolsTable.variables.containsKey(lexeme))
            throw new UndefinedVariableException(row, col, lexeme);

        TypeNode type = SymbolsTable.variables.get(lexeme);
        for(Attributes attr : attributes) {
            type = attr.evaluateType(type);
        }

        return type;
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}
