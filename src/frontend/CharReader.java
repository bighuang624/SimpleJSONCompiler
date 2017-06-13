package frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author huang 
 * 读取字符，同时用于确定 token 的位置
 */
public class CharReader implements AutoCloseable {

	public static final char EOF = (char) 0;
	public static final char EOL = '\n';
	private BufferedReader reader;
	private String line;
	private int currentLine = 0;
	private int currentPosition = 1;

	public CharReader(String filePath) {
		try {
			this.reader = new BufferedReader(new FileReader(filePath));
			line = readNextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getCurrentLine() {
		return currentLine;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public char nextChar() {
		currentPosition++;
		if (line == null) {
			return CharReader.EOF;
		} else if (line.charAt(currentPosition - 1) == CharReader.EOL || line.length() == 0) {
			line = readNextLine();
			return nextChar();
		}
		return line.charAt(currentPosition - 1);
	}

	private char peekChar(int offset) {
		if ("".equals(line) || line == null) {
			return nextChar();
		}
		int index = currentPosition - 1 + offset;
		if (index >= line.length() || index <= -1) {
			return CharReader.EOL;
		}
		return line.charAt(index);
	}

	public char peekChar() {
		return peekChar(1);
	}

	private String readNextLine() {
		String str = null;
		try {
			str = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentPosition = 0;
		if (str != null) {
			this.currentLine++;
			str += CharReader.EOL;
		}
		return str;
	}

	public void close() throws IOException {
		if (reader != null) {
			reader.close();
		}
	}
}
