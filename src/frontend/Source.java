package frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author huang
 * 用于确定 token 的位置
 */
public class Source {
	
	public static final char EOF = (char) 0;  // end of file
	public static final char EOL = '\n';  // end of line
    private BufferedReader source;  // reader for the source file
    private String line;  // the content of the line
    private int currentLine = 0;
    private int currentPosition;
    
    public Source(String filePath){
    	try {
			this.source = new BufferedReader(new FileReader(filePath));
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
	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
    
	public char nextChar(){
		this.currentPosition++;
		if(line == null){
			return Source.EOF;
		}else if(line.charAt(currentPosition) == Source.EOL || line.length() == 0){
			line = readNextLine();
			return nextChar();
		}
		return line.charAt(currentPosition);
	}
	
	
	/**
	 * get char at specified offset (default 1)
	 * if offset is too big or too small, returns end of line
	 * 应该可以改成 private?
	 * @param offset
	 * @return
	 */
	public char peekChar(int offset){
		if(line == null || "".equals(line)){
			return nextChar();
		}
		int index = currentPosition + offset;
		if(index >= line.length() || index <= -1){
			return Source.EOL;
		}
		return line.charAt(index);
	}
	
	public char peekChar(){
		return peekChar(1);
	}
    
	/**
	 * why private here?
	 * @return
	 */
	private String readNextLine(){
		String str = null;
		try {
			str = source.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentPosition = -1;
		if(str != null){
			currentLine++;
			str+=Source.EOL;
		}
		return str;
	}
	
	public void close() throws IOException{
		if(source != null){
			source.close();
		}
	}
}
