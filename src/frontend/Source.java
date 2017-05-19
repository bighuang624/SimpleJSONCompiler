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
    private String line;
    private int currentLine = 0;
    private int currentPosition;
    
    public Source(String filePath){
    	try {
			this.source = new BufferedReader(new FileReader(filePath));
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
		
	}
	
	public char peekChar(){
		
	}
    
	private String readNextLine(){
		
	}
	
	public void close() throws IOException{
		if(source != null){
			source.close();
		}
	}
}
