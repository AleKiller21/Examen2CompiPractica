package SyntaxAnalyser.ParserExceptions;


public class IdExpectedException extends ParserException {
    public IdExpectedException(int row, int col) {
        super(String.format("Id token expected at row %d column %d", row, col));
    }
}
