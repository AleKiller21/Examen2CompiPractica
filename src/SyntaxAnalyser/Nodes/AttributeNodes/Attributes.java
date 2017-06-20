package SyntaxAnalyser.Nodes.AttributeNodes;


import SyntaxAnalyser.Nodes.Expressions.ExpressionCode;
import SyntaxAnalyser.Nodes.LineNumbering;
import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

public abstract class Attributes extends LineNumbering {
    public abstract TypeNode evaluateType(TypeNode type) throws Exception;
    public abstract ExpressionCode generateCode();
}
