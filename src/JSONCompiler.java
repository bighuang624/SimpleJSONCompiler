import frontend.CharReader;
import frontend.JsonWriter;
import frontend.Lexer;
import frontend.Parser;
import frontend.Searcher;
import frontend.StackElement;
import frontend.util.Token;

/**
 * @author huang 
 * 程序入口
 */
public class JSONCompiler {
	public static void main(String[] args) {
		try {
			if (args.length == 3 && args[0].equals("-find")) {
				search(args[1], args[2]);
			} else if (args.length == 2 && args[0].equals("-pretty")) {
				pretty(args[1]);
			} else if (args.length == 1) {
				parse(args[0]);
			} else {
				System.out.println("Arguments are invalid!\n");
				helpDocument();
			}
		} catch (Exception e) {

		}
	}

	private static void helpDocument() {
		System.out.println("Usage: java -jar jsonCompiler.jar [options] sourceFile");
		System.out.println("Just show whether the file is valid without options");
		System.out.println("options:");
		System.out.println("\t-find <path>, to find the specified value and show its type,");
		System.out.println("\tthe format of <path> for example: \"/RECORDS[35]/countryname\"");
		System.out.println("\n\t-pretty, to write a json file with better output format");
	}

	private static void parse(String file) throws Exception {
		CharReader reader = new CharReader(file);
		Lexer lexer = new Lexer(reader);
		Parser parser = new Parser(lexer);
		parser.parse();
		System.out.println("valid");
	}

	private static void pretty(String filePath) throws Exception {
		CharReader reader = new CharReader(filePath);
		Lexer lexer = new Lexer(reader);
		Parser parser = new Parser(lexer);
		JsonWriter.writeJson(parser.parse().getValue(), filePath);
	}

	private static void search(String searchPath, String filePath) throws Exception {
		CharReader reader = new CharReader(filePath);
		Lexer lexer = new Lexer(reader);
		Parser parser = new Parser(lexer);
		Searcher searcher = new Searcher(parser);
		StackElement result = searcher.search(searchPath);
		if (null == result.getValue()) {
			System.out.println("null");
		} else {
			System.out.println("The search result is: " + result);
			if (result.getType().equals(StackElement.ARRAY)) {
				System.out.println("The class of the result is: array");
			} else if (result.getType().equals(StackElement.OBJECT)) {
				System.out.println("The class of the result is: object");
			} else {
				System.out.println("The class of the result is: " + ((Token) result.getValue()).getType());
			}
		}
	}

}
