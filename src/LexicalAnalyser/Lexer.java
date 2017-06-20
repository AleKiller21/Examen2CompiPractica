package LexicalAnalyser;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    private InputStream input;
    private Symbol currentSymbol;
    private HashMap<String, TokenType> reservedWords;
    private HashMap<String, TokenType> operators;
    private HashMap<String, TokenType> punctuationSymbols;

    public Lexer(String inputStream) {
        input = new InputStream(inputStream);
        currentSymbol = input.GetNextSymbol();
        initReservedWords();
        initOperators();
        initSymbols();
    }

    public ArrayList<Token> GetTokens() throws Exception {
        ArrayList<Token> tokens = new ArrayList<>();
        Token current = GetNextToken();
        while(current.type != TokenType.Eof) {
            tokens.add(current);
            current = GetNextToken();
        }

        return tokens;
    }

    public Token GetNextToken() throws Exception {
        while(currentSymbol.symbol == '\n' || Character.isWhitespace(currentSymbol.symbol))
            currentSymbol = input.GetNextSymbol();

        if(Character.isLetter(currentSymbol.symbol)) return GetIdentifierToken();
        if(Character.isDigit(currentSymbol.symbol)) return GetNumToken();
        if(currentSymbol.symbol == '$') return new Token(currentSymbol.symbol+"", TokenType.Eof,
                currentSymbol.row, currentSymbol.col);


        TokenType type = reservedWords.get(currentSymbol.symbol+"");
        if(type != null) {
            String lexeme = currentSymbol.symbol+"";
            currentSymbol = input.GetNextSymbol();
            return new Token(lexeme, type, currentSymbol.row, currentSymbol.col);
        }

        type = operators.get(currentSymbol.symbol+"");
        if(type != null) {
            String lexeme = currentSymbol.symbol+"";
            currentSymbol = input.GetNextSymbol();
            return new Token(lexeme, type, currentSymbol.row, currentSymbol.col);
        }

        type = punctuationSymbols.get(currentSymbol.symbol+"");
        if(type != null) {
            String lexeme = currentSymbol.symbol+"";
            currentSymbol = input.GetNextSymbol();
            return new Token(lexeme, type, currentSymbol.row, currentSymbol.col);
        }

        throw new Exception("Unrecognized token at row " + currentSymbol.row + " column " + currentSymbol.col);
    }

    private Token GetNumToken() {
        StringBuilder lexeme = new StringBuilder(currentSymbol.symbol + "");
        int row = currentSymbol.row;
        int col = currentSymbol.col;

        currentSymbol = input.GetNextSymbol();
        while(Character.isDigit(currentSymbol.symbol)) {
            lexeme.append(currentSymbol.symbol);
            currentSymbol = input.GetNextSymbol();
        }

        return new Token(lexeme.toString(), TokenType.LiteralNum, row, col);
    }

    private Token GetIdentifierToken() {
        StringBuilder lexeme = new StringBuilder(currentSymbol.symbol + "");
        int row = currentSymbol.row;
        int col = currentSymbol.col;

        currentSymbol = input.GetNextSymbol();
        while(Character.isLetterOrDigit(currentSymbol.symbol)) {
            lexeme.append(currentSymbol.symbol);
            currentSymbol = input.GetNextSymbol();
        }

        TokenType type = reservedWords.get(lexeme.toString());
        if(type != null) return new Token(lexeme.toString(), type, row, col);
        else return new Token(lexeme.toString(), TokenType.ID, row, col);
    }

    private void initSymbols() {
        punctuationSymbols = new HashMap<>();
        punctuationSymbols.put("(", TokenType.ParenthesisOpen);
        punctuationSymbols.put(")", TokenType.ParenthesisClose);
        punctuationSymbols.put("[", TokenType.SquareBracketOpen);
        punctuationSymbols.put("]", TokenType.SquareBracketClose);
        punctuationSymbols.put(";", TokenType.EndStatement);
    }

    private void initOperators() {
        operators = new HashMap<>();
        operators.put("+", TokenType.OpSum);
        operators.put("-", TokenType.OpSub);
        operators.put("*", TokenType.OpMult);
        operators.put("/", TokenType.OpDiv);
        operators.put("=", TokenType.OpAssign);
        operators.put(".", TokenType.OpMemberAccess);
    }

    private void initReservedWords() {
        reservedWords = new HashMap<>();
        reservedWords.put("print", TokenType.RwPrint);
        reservedWords.put("read", TokenType.RwRead);
        reservedWords.put("decl", TokenType.RwDeclare);
        reservedWords.put("struct", TokenType.RwStruct);
        reservedWords.put("true", TokenType.LiteralTrue);
        reservedWords.put("false", TokenType.LiteralFalse);
        reservedWords.put("int", TokenType.RwInt);
        reservedWords.put("bool", TokenType.RwBool);
        reservedWords.put("end", TokenType.RwEnd);
    }
}
