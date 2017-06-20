package LexicalAnalyser;


public enum TokenType {
    ID,
    LiteralNum,
    LiteralTrue,
    LiteralFalse,
    EndStatement,
    OpAssign,
    OpSum,
    OpSub,
    OpMult,
    OpDiv,
    OpMemberAccess,
    RwPrint,
    RwRead,
    RwStruct,
    RwDeclare,
    RwInt,
    RwBool,
    RwEnd,
    ParenthesisOpen,
    ParenthesisClose,
    SquareBracketOpen,
    SquareBracketClose,
    Eof
}
