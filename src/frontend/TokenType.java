package frontend;

import java.util.EnumSet;

/**
 * @author huang
 * Token 的类型
 */
public enum TokenType {
	
	IDENTIFIER("identifer");
	
	public String literal;
	private TokenType(String literal){
		this.literal = literal;
	}
//    public static EnumSet<TokenType> OP = EnumSet.range(from, to);
}
