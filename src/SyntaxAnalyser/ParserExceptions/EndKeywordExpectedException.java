package SyntaxAnalyser.ParserExceptions;


public class EndKeywordExpectedException extends ParserException {
    public EndKeywordExpectedException(int row, int col) {
        super(String.format("'end' keyword expected at row %d column %d", row, col));
    }
}
