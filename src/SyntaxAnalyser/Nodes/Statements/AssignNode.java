package SyntaxAnalyser.Nodes.Statements;

import SemanticExceptions.SemanticException;
import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.Expressions.IdNode;
import SyntaxAnalyser.Nodes.SymbolsTable;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public class AssignNode extends StatementNode {
    public IdNode id;
    public ExpressionNode value;

    public AssignNode(IdNode id, ExpressionNode value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public void validateSemantic() throws Exception {
        if(SymbolsTable.variables.containsKey(id.toString())) {
            TypeNode idType = SymbolsTable.variables.get(id.toString());
            TypeNode valueType = value.evaluateType();
            if(!idType.toString().equals(valueType.toString()))
                throw new SemanticException(String.format("The value type to be assigned at row %d column %d is not" +
                        "compatible with variable type", row, col));
        }
        else {
            TypeNode valueType = value.evaluateType();
            SymbolsTable.variables.put(id.toString(), valueType);
        }
    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
