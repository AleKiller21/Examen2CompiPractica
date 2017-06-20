package SyntaxAnalyser.ParserExceptions;


public class StructKeywordExpectedException extends ParserException {
    public StructKeywordExpectedException(int row, int col) {
        super(String.format("'struct' keyword expected at row %d column %d", row, col));
    }
}
