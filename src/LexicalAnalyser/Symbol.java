package LexicalAnalyser;


public class Symbol {
    public char symbol;
    public int row;
    public int col;

    public Symbol(char symbol, int row, int col) {
        this.symbol = symbol;
        this.row = row;
        this.col = col;
    }
}
