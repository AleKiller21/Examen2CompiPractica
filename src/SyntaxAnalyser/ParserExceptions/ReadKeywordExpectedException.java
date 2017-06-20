package SyntaxAnalyser.ParserExceptions;


public class ReadKeywordExpectedException extends ParserException {
    public ReadKeywordExpectedException(int row, int col) {
        super(String.format("'read' keyword expected at row %d column %d", row, col));
    }
}
