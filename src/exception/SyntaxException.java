package exception;

/**
 * @author huang
 * 
 */
public class SyntaxException extends Exception{

	private static final long serialVersionUID = 1L;

	public SyntaxException(String errmsg) {
		System.err.println("Error: " + errmsg);
	}

	public SyntaxException(String errmsg, int line) {
		System.err.println("Error(line " + line + "): " + errmsg);
	}

	public SyntaxException(String errmsg, int line, int position) {
		System.err.println("Error(line " + line + ",position " + position + "): " + errmsg);
	}
	
	// for test
//	public SyntaxException(String errmsg, int line, int position, String value) {
//		System.err.println("Error(line " + line + ",position " + position + "): " + errmsg + value);
//	}
}
