package exception.stackException;

public class EmptyStackException extends Exception {

		private static final long serialVersionUID = 1L;

		public EmptyStackException(String errmsg) {
			System.err.println("Error: " + errmsg);
		}

		public EmptyStackException(String errmsg, int line) {
			System.err.println("Error(line " + line + "): " + errmsg);
		}

		public EmptyStackException(String errmsg, int line, int position) {
			System.err.println("Error(line " + line + ",position " + position + "): " + errmsg);
		}
}
