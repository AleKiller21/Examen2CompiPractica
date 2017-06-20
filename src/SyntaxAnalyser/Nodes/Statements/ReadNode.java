package SyntaxAnalyser.Nodes.Statements;

import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.Expressions.IdNode;


public class ReadNode extends StatementNode {
    public IdNode id;

    public ReadNode(IdNode id) {
        this.id = id;
    }

    @Override
    public void validateSemantic() {

    }

    @Override
    public ExpressionCode generateCode() {
        return null;
    }
}
