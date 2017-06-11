package frontend.util;

/**
 * @author huang 
 * Token 类
 */
public class Token {
	private TokenType type;
	private String value;
	private int line;
	private int position;

	public Token() {
	}

	public Token(TokenType type, String value, int line, int position) {
		this.type = type;
		this.value = value;
		this.line = line;
		this.position = position;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getLine() {
		return line;
	}

	public int getPosition() {
		return position;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean is(TokenType tokentype) {
		return type.equals(tokentype);
	}

	@Override
	public String toString() {
		if(type.equals(TokenType.STRING)){
			return "\"" + value + "\"";
		}
		return value;
	}

}
