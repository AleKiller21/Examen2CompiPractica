package SyntaxAnalyser.ParserExceptions;


public class NumericConstantExpectedException extends Exception {
    public NumericConstantExpectedException(int row, int col) {
        super(String.format("Numeric constant expected within rank specifier at row %d column %d", row, col));
    }
}
