package SyntaxAnalyser.Nodes.Statements;


import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.DataTypeNode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.IdNode;
import SyntaxAnalyser.Nodes.SymbolsTable;
import SyntaxAnalyser.Nodes.TypeNodes.ArrayType;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

import java.util.ArrayList;

public class DeclarationStatement extends StatementNode {
    public DataTypeNode type;
    public IdNode id;
    public ArrayList<Integer> rankSpecifier;

    public DeclarationStatement(DataTypeNode type, String id, ArrayList<Integer> rankSpecifier) {
        this.type = type;
        this.id = new IdNode(id);
        this.rankSpecifier = rankSpecifier;
    }

    @Override
    public void validateSemantic() throws Exception {
        if(SymbolsTable.variables.containsKey(id.toString()))
            throw new SemanticException(String.format("Variable at row %d column %d is already declared.", row, col));

        TypeNode type = this.type.evaluateType();
        if(rankSpecifier.size() == 1) {
            ArrayType arrayType = new ArrayType(rankSpecifier.get(0));
            arrayType.type = type;
            SymbolsTable.variables.put(id.toString(), arrayType);
        }

        else if(rankSpecifier.size() >= 2) {
            ArrayType arrayType = new ArrayType(rankSpecifier.get(0));
            TypeNode currentType = arrayType;
            for(int i = 1; i < rankSpecifier.size(); i++) {
                ((ArrayType)currentType).type = new ArrayType(rankSpecifier.get(i));
                currentType = arrayType.type;
            }

            ((ArrayType)currentType).type = type;
            SymbolsTable.variables.put(id.toString(), currentType);
        }

        else SymbolsTable.variables.put(id.toString(), type);
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
