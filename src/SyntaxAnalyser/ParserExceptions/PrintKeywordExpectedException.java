package SyntaxAnalyser.ParserExceptions;


public class PrintKeywordExpectedException extends ParserException {
    public PrintKeywordExpectedException(int row, int col) {
        super(String.format("'print' keyword expected at row %d column %d", row, col));
    }
}
