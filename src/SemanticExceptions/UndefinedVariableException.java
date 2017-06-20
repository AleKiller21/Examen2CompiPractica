package SemanticExceptions;


public class UndefinedVariableException extends Exception {
    public UndefinedVariableException(int row, int col, String lexeme) {
        super(String.format("Undefined variable %s found at row %d column %d", lexeme, row, col));
    }
}
