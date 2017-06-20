package SyntaxAnalyser;

import LexicalAnalyser.*;
import SyntaxAnalyser.Nodes.AttributeNodes.Attributes;
import SyntaxAnalyser.Nodes.AttributeNodes.FieldNode;
import SyntaxAnalyser.Nodes.AttributeNodes.IndexArrayNode;
import SyntaxAnalyser.Nodes.DataTypeNode;
import SyntaxAnalyser.Nodes.Expressions.BoolNode;
import SyntaxAnalyser.Nodes.Expressions.ExpressionNode;
import SyntaxAnalyser.Nodes.Expressions.IdNode;
import SyntaxAnalyser.Nodes.Expressions.IntNode;
import SyntaxAnalyser.Nodes.Operators.DivOperator;
import SyntaxAnalyser.Nodes.Operators.MultOperator;
import SyntaxAnalyser.Nodes.Operators.SubNode;
import SyntaxAnalyser.Nodes.Operators.SumNode;
import SyntaxAnalyser.Nodes.Statements.*;
import SyntaxAnalyser.ParserExceptions.*;

import java.util.ArrayList;

public class Parser {
    private Lexer lex;
    private Token currentToken;

    public Parser(Lexer lex) throws Exception {
        this.lex = lex;
        currentToken = this.lex.GetNextToken();
    }

    public ArrayList<StatementNode> Parse() throws Exception {
        return StatementsProduction();
    }

    private ArrayList<StatementNode> StatementsProduction() throws Exception {
        if(CheckCurrentToken(TokenType.ID, TokenType.RwPrint, TokenType.RwRead,
                TokenType.RwDeclare, TokenType.RwStruct))
        {
            StatementNode statement = StatementProduction();
            ArrayList<StatementNode> statements = StatementsProduction();

            statements.add(0, statement);
            return statements;
        }

        return new ArrayList<>();
    }

    private StatementNode StatementProduction() throws Exception {
        if(CheckCurrentToken(TokenType.ID)) return AssignProduction();
        else if(CheckCurrentToken(TokenType.RwPrint)) return PrintProduction();
        else if(CheckCurrentToken(TokenType.RwRead)) return ReadProduction();
        else if(CheckCurrentToken(TokenType.RwDeclare)) return DeclareStatementProduction();
        else if(CheckCurrentToken(TokenType.RwStruct)) return StructDeclarationProduction();
        else throw new ParserException(String.format("Unexpected token found at row %d column %d",
                    GetTokenRow(), GetTokenColumn()));
    }

    private StatementNode AssignProduction() throws Exception {
        if(!CheckCurrentToken(TokenType.ID))
            throw new IdExpectedException(GetTokenRow(), GetTokenColumn());

        IdNode id = ID();
        if(!CheckCurrentToken(TokenType.OpAssign))
            throw new AssignOperatorExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        ExpressionNode value = E();

        if(!CheckCurrentToken(TokenType.EndStatement))
            throw new EndOfStatementExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        return new AssignNode(id, value);
    }

    private IdNode ID() throws Exception {
        String id = currentToken.lexeme;
        NextToken();
        ArrayList<Attributes> attrs = OptionalAttributeList();

        return new IdNode(id, attrs);
    }

    private ArrayList<Attributes> OptionalAttributeList() throws Exception {
        if(CheckCurrentToken(TokenType.OpMemberAccess)) {
            NextToken();
            if(!CheckCurrentToken(TokenType.ID))
                throw new IdExpectedException(GetTokenRow(), GetTokenColumn());

            String id = currentToken.lexeme;
            NextToken();
            ArrayList<Attributes> attrs = OptionalAttributeList();
            attrs.add(0, new FieldNode(id));
            return attrs;
        }

        if(CheckCurrentToken(TokenType.SquareBracketOpen)) {
            NextToken();
            ExpressionNode expression = E();

            if(!CheckCurrentToken(TokenType.SquareBracketClose))
                throw new ParserException(String.format("']' token expected at row %d column %d",
                        GetTokenRow(), GetTokenColumn()));

            NextToken();
            ArrayList<Attributes> attrs = OptionalAttributeList();
            attrs.add(0, new IndexArrayNode(expression));
            return attrs;
        }

        return new ArrayList<>();
    }

    private PrintNode PrintProduction() throws Exception {
        if(!CheckCurrentToken(TokenType.RwPrint))
            throw new PrintKeywordExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        ExpressionNode value = E();

        if(!CheckCurrentToken(TokenType.EndStatement))
            throw new EndOfStatementExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        return new PrintNode(value);
    }

    private ReadNode ReadProduction() throws Exception {
        if(!CheckCurrentToken(TokenType.RwRead))
            throw new ReadKeywordExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        if(!CheckCurrentToken(TokenType.ID))
            throw new IdExpectedException(GetTokenRow(), GetTokenColumn());

        String id = currentToken.lexeme;
        NextToken();
        if(!CheckCurrentToken(TokenType.EndStatement))
            throw new EndOfStatementExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        return new ReadNode(new IdNode(id));
    }

    private DeclarationStatement DeclareStatementProduction() throws Exception {
        if(!CheckCurrentToken(TokenType.RwDeclare))
            throw new DeclareKeywordExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        if(!CheckCurrentToken(TokenType.RwInt, TokenType.RwBool, TokenType.ID))
            throw new ParserException(String.format("'int', 'bool', or id token expected at row %d column %d",
                    GetTokenRow(), GetTokenColumn()));

        String typeId = currentToken.lexeme;
        NextToken();
        if(!CheckCurrentToken(TokenType.ID))
            throw new IdExpectedException(GetTokenRow(), GetTokenColumn());

        String id = currentToken.lexeme;
        NextToken();
        ArrayList<Integer> rankSpecifier = OptionalRankSpecifier();
        if(!CheckCurrentToken(TokenType.EndStatement))
            throw new EndOfStatementExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        return new DeclarationStatement(new DataTypeNode(typeId), id, rankSpecifier);
    }

    private ArrayList<Integer> OptionalRankSpecifier() throws Exception {
        if(CheckCurrentToken(TokenType.SquareBracketOpen)) {
            NextToken();
            if(!CheckCurrentToken(TokenType.LiteralNum))
                throw new NumericConstantExpectedException(GetTokenRow(), GetTokenColumn());

            int rank = Integer.parseInt(currentToken.lexeme);
            NextToken();
            if(!CheckCurrentToken(TokenType.SquareBracketClose))
                throw new ParserException(String.format("']' token expected at row %d column %d",
                        GetTokenRow(), GetTokenColumn()));

            NextToken();
            ArrayList<Integer> ranks = OptionalRankSpecifier();
            ranks.add(0, rank);

            return ranks;
        }

        return new ArrayList<>();
    }

    private StructNode StructDeclarationProduction() throws Exception {
        if(!CheckCurrentToken(TokenType.RwStruct))
            throw new StructKeywordExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        if(!CheckCurrentToken(TokenType.ID))
            throw new IdExpectedException(GetTokenRow(), GetTokenColumn());

        String id = currentToken.lexeme;
        NextToken();
        ArrayList<DeclarationStatement> fields = OptionalStructFieldDeclaration();
        if(!CheckCurrentToken(TokenType.RwEnd))
            throw new EndKeywordExpectedException(GetTokenRow(), GetTokenColumn());

        NextToken();
        return new StructNode(id, fields);
    }

    private ArrayList<DeclarationStatement> OptionalStructFieldDeclaration() throws Exception {
        if(CheckCurrentToken(TokenType.RwDeclare)) {
            DeclarationStatement field = DeclareStatementProduction();
            ArrayList<DeclarationStatement> fields = OptionalStructFieldDeclaration();

            fields.add(0, field);
            return fields;
        }

        return new ArrayList<>();
    }

    private ExpressionNode E() throws Exception {
        ExpressionNode value = T();
        return EPrime(value);
    }

    private ExpressionNode EPrime(ExpressionNode leftOperand) throws Exception {
        if(CheckCurrentToken(TokenType.OpSum)) {
            NextToken();
            ExpressionNode rightOperand = T();
            return EPrime(new SumNode(leftOperand, rightOperand));
        }

        else if(CheckCurrentToken(TokenType.OpSub)) {
            NextToken();
            ExpressionNode rightOperand = T();
            return EPrime(new SubNode(leftOperand, rightOperand));
        }

        return leftOperand;
    }

    private ExpressionNode T() throws Exception {
        ExpressionNode value = F();
        return TPrime(value);
    }

    private ExpressionNode TPrime(ExpressionNode leftOperand) throws Exception {
        if(CheckCurrentToken(TokenType.OpMult)) {
            NextToken();
            ExpressionNode rightOperand = F();
            return TPrime(new MultOperator(leftOperand, rightOperand));
        }

        else if(CheckCurrentToken(TokenType.OpDiv)) {
            NextToken();
            ExpressionNode rightOperand = F();
            return TPrime(new DivOperator(leftOperand, rightOperand));
        }

        return leftOperand;
    }

    private ExpressionNode F() throws Exception {
        if(CheckCurrentToken(TokenType.ParenthesisOpen)) {
            NextToken();
            ExpressionNode value = E();
            if(!CheckCurrentToken(TokenType.ParenthesisClose)) {
                throw new ParserException(String.format("')' token expected at row %d column %d",
                        GetTokenRow(), GetTokenColumn()));
            }

            NextToken();
            return value;
        }

        else if(CheckCurrentToken(TokenType.LiteralNum)) {
            int num = Integer.parseInt(currentToken.lexeme);
            NextToken();
            return new IntNode(num);
        }
        else if(CheckCurrentToken(TokenType.ID)) {
            return ID();
        }
        else if(CheckCurrentToken(TokenType.LiteralTrue, TokenType.LiteralFalse)) {
            boolean value = Boolean.parseBoolean(currentToken.lexeme);
            NextToken();
            return new BoolNode(value);
        }
        else throw new ParserException(String.format("'(', numeric, or id token expected at beginning of expression at " +
                    "row %d column %d", GetTokenRow(), GetTokenColumn()));
    }

    private boolean CheckCurrentToken(TokenType... types) {
        for(TokenType type : types) {
            if(currentToken.type == type) return true;
        }

        return false;
    }

    private int GetTokenRow() {
        return currentToken.row;
    }

    private int GetTokenColumn() {
        return currentToken.col;
    }

    private void NextToken() throws Exception {
        currentToken = lex.GetNextToken();
    }

    private boolean IsExpression() {
        return CheckCurrentToken(TokenType.ParenthesisOpen, TokenType.LiteralNum, TokenType.ID);
    }
}
