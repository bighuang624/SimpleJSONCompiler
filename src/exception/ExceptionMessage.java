package exception;

/**
 * @author huang
 * 报错信息
 */

public class ExceptionMessage {
    public static final String ILLEGAL_TOKEN = "Illegal token";  // lexer读取到不合法的token
    public static final String EXPECTED_BACKQUOTE = "Expected <\">";  // 字符串缺少反引号
    public static final String UNEXPECTED_CHAR = "Unexpected char in token: ";  // 不应该出现的字符
    public static final String ILLEGAL_NUMBER = "Illegal number";  // 数不合法，可能是缺少整数、实数或次幂中的某一部分，或者数中掺杂非数字
    public static final String UNEXPECTED_TOKEN = "Token in an unexpcected place OR some expected tokens missed";  // token 出现在不合适的地方，或者缺少某些 token
    public static final String UNEXPECTED_COMMA = "Token <,> in an unexpcected place";
    public static final String UNEXPECTED_COLON = "Token <:> in an unexpcected place";
    public static final String UNEXPECTED_STRING = "String token in an unexpcected place";
    public static final String UNEXPECTED_EOF = "Unexpected EOF";  //
    public static final String UNMATCHED_OBORARR = "Unmatched object or array";
    public static final String STACKOVERFLOW = "Stackoverflow exception";
    public static final String EMPTY_STACK = "Empty stack exception";
}
