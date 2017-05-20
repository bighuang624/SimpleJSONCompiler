package exception;

/**
 * @author huang
 * 
 */
public class LexException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LexException(String errmsg) {
		System.err.println("Error: " + errmsg);
	}

	public LexException(String errmsg, int line) {
		System.err.println("Error(line " + line + "): " + errmsg);
	}

	public LexException(String errmsg, int line, int position) {
		System.err.println("Error(line " + line + ",position " + position + "): " + errmsg);
	}
}
