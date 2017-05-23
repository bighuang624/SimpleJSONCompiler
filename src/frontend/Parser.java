package frontend;

import java.util.LinkedList;

/**
 * @author huang
 *
 */
public class Parser {
    private Token cursor;
    private LinkedList<Token> tokenList = new LinkedList<>();
    // 缺少一个东西
    private Lexer lexer;
    
    public Parser(Lexer lexer){
    	this.tokenList = new LinkedList<>();
    	
    	this.lexer = lexer;
    }
    
    
    
    
    
}
