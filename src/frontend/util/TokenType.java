package frontend.util;

/**
 * @author huang
 * Token 的类型
 */
public enum TokenType {
	
	OBJECT("object"),
	ARRAY("array"),
	MEMBERS("members"),
	PAIR("pair"),
	STRING("string"),
	VALUE("value"),
	ELEMENTS("elements"),
	NUMBER("number"),
	INTEGER("integer"),
	FLOAT("float"),
	SCIENTIFIC("scientific"),
	
	LEFT_BRACE("{"),
	RIGHT_BRACE("}"),
	LEFT_BRACKET("["),
	RIGHT_BRACKET("]"),
	COMMA(","),
	COLON(":"),
	
	TRUE("true"),
	FALSE("false"),
	NULL("null"),
	
	EOF("end_of_file");
	
	public String literal;
	private TokenType(String literal){
		this.literal = literal;
	}
}
