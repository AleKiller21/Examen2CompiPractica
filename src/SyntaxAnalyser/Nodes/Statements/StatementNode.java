package SyntaxAnalyser.Nodes.Statements;

import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.LineNumbering;

public abstract class StatementNode extends LineNumbering {
    public abstract void validateSemantic() throws Exception;
    public abstract ExpressionCode generateCode();
}
