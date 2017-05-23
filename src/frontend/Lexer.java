package frontend;

import exception.ExceptionMessage;
import exception.LexException;

/**
 * @author huang
 *
 */
public class Lexer {
	private Source source;
	
    public Lexer(Source source){
    	this.source = source;
    }
    
    public boolean hasNext(){
    	return source.peekChar() != Source.EOF;
    }
    
    public Token next() throws LexException{
    	char ch = source.nextChar();
    	// read next char if lexer meets white space
    	while(Character.isWhitespace(ch)){
    		ch = source.nextChar();
    	}
    	// number，包括integer、float、scientific（分开）
    	if(Character.isDigit(ch) || ch == '-'){
    		final StringBuilder number = new StringBuilder();
    		if(ch == '-'){
    			number.append(ch);
    			ch = source.nextChar();
    		}
    		int offset = 0;
    		
    		// unfinished
    		
    		return new Token(TokenType.NUMBER, number.toString(),
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// String
    	else if(ch == '"'){
    		StringBuilder string = new StringBuilder();
    		ch = source.nextChar();
    		string.append(ch);
    		int offset = 0;
    		// 有点问题，这里可以是任何unicode
    		while(Character.isAlphabetic(source.peekChar()) || 
    				Character.isDigit(source.peekChar())){
    			ch = source.nextChar();
    			++offset;
    			if(ch == Source.EOF){
    				break;
    			}
    			string.append(ch);
    		}
    		// 反引号
    		if(source.peekChar() == '"'){
    			ch = source.nextChar();
    		}

    		return new Token(TokenType.STRING, string.toString(),
    				source.getCurrentLine(), source.getCurrentPosition() - offset);    		
    	}
    	// true
    	else if(ch == 't'){
    		int tokenOffset = 3;
    		for(int i = 0; i < tokenOffset; i++){
    			ch = source.nextChar();
    		}
    			
    		return new Token(TokenType.TRUE, "true",
    				source.getCurrentLine(), source.getCurrentPosition() - tokenOffset);
    	}
    	// false
    	else if(ch == 'f'){
    		int tokenOffset = 4;
    		for(int i = 0; i < tokenOffset; i++){
    			ch = source.nextChar();
    		}
    		
    		return new Token(TokenType.FLOAT, "false",
    				source.getCurrentLine(), source.getCurrentPosition() - tokenOffset);
    	}
    	// null
    	else if(ch == 'n'){
    		int tokenOffset = 3;
    		for(int i = 0; i < tokenOffset; i++){
    			ch = source.nextChar();
    		}
    		
    		return new Token(TokenType.NULL, "null",
    				source.getCurrentLine(), source.getCurrentPosition() - tokenOffset);
    	}
    	// [
    	else if(ch == '['){
    		return new Token(TokenType.LEFT_BRACKET, "[",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// ]
    	else if(ch == ']'){
    		return new Token(TokenType.RIGHT_BRACKET, "]",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// {
    	else if(ch == '{'){
    		return new Token(TokenType.LEFT_BRACE, "{",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// }
    	else if(ch == '}'){
    		return new Token(TokenType.RIGHT_BRACE, "}",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// :
    	else if(ch == ':'){
    		return new Token(TokenType.COMMA, ":",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// ,
    	else if(ch == ','){
    		return new Token(TokenType.COLON, ",",
    				source.getCurrentLine(), source.getCurrentPosition());
    	}
    	// error，须改进
    	else{
    		final Token error = new Token(TokenType.ERROR, ExceptionMessage.ILLEGAL_TOKEN,
    				source.getCurrentLine(), source.getCurrentPosition());  // 不一定需要
    		throw new LexException(ExceptionMessage.ILLEGAL_TOKEN, source.getCurrentLine(), 
    				source.getCurrentPosition());
    	}
    }
}
