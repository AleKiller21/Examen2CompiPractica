package SyntaxAnalyser.ParserExceptions;


public class EndOfStatementExpectedException extends ParserException {
    public EndOfStatementExpectedException(int row, int col) {
        super(String.format("';' token expected at row %d column %d", row, col));
    }
}
