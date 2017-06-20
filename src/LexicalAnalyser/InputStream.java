package LexicalAnalyser;


public class InputStream {
    private String inputString;
    private int row;
    private int col;
    private int iterator;

    public InputStream(String inputString) {
        this.inputString = inputString;
        row = 1;
        col = 1;
        iterator = 0;
    }

    public Symbol GetNextSymbol() {
        if(iterator == inputString.length())
            return new Symbol('$', row, col);

        if(inputString.charAt(iterator) == '\n') {
            int column = col;
            col = 1;
            return new Symbol(inputString.charAt(iterator++), row++, column);
        }

        return new Symbol(inputString.charAt(iterator++), row, col++);
    }
}
