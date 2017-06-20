package SyntaxAnalyser.Nodes.Expressions;


import SyntaxAnalyser.Nodes.LineNumbering;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public abstract class ExpressionNode extends LineNumbering {
    public abstract TypeNode evaluateType() throws Exception;
    public abstract ExpressionCode generateCode();
}
