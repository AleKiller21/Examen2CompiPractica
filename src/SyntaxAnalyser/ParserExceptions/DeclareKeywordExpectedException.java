package SyntaxAnalyser.ParserExceptions;


public class DeclareKeywordExpectedException extends ParserException {
    public DeclareKeywordExpectedException(int row, int col) {
        super(String.format("'decl' keyword expected at row %d column %d", row, col));
    }
}
