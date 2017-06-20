package SyntaxAnalyser.ParserExceptions;


public class AssignOperatorExpectedException extends ParserException {
    public AssignOperatorExpectedException(int row, int col) {
        super(String.format("'=' operator expected at row %d column %d", row, col));
    }
}
