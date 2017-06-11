package exception.stackException;

public class StackOverflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public StackOverflowException(String errmsg) {
		System.err.println("Error: " + errmsg);
	}

	public StackOverflowException(String errmsg, int line) {
		System.err.println("Error(line " + line + "): " + errmsg);
	}

	public StackOverflowException(String errmsg, int line, int position) {
		System.err.println("Error(line " + line + ",position " + position + "): " + errmsg);
	}
}
