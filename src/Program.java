import LexicalAnalyser.*;
import SyntaxAnalyser.*;
import SyntaxAnalyser.Nodes.Statements.StatementNode;

import java.util.ArrayList;

public class Program {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("A = 1;\nB=3;\nC=A+B;\nprint C*2;\nread B;\nprint (A+B)/2;");
        Parser parser = new Parser(lex);
        ArrayList<StatementNode> parse = parser.Parse();
        System.out.println("SUCCESS!!");
    }
}
