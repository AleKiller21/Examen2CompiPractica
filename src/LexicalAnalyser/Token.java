package LexicalAnalyser;

public class Token {
    public String  lexeme;
    public TokenType type;
    public int row;
    public int col;

    public Token(String lexeme, TokenType type, int row, int col) {
        this.lexeme = lexeme;
        this.type = type;
        this.row = row;
        this.col = col;
    }
}
