import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import frontend.CharReader;
import frontend.Lexer;
import frontend.Parser;

/**
 * @author huang
 * 程序入口
 */
public class JSONCompiler {
	public static void main(String[] args) throws Exception {
		if (args.length == 3 && args[0].equals("-find")) {
			System.out.println("-find 正在开发中");
		} else if (args.length == 2 && args[0].equals("-pretty")) {
			// System.out.println("-pretty 正在开发中");

			String filePath = args[1];
			CharReader reader = new CharReader(filePath);
			Lexer lexer = new Lexer(reader);
			Parser parser = new Parser(lexer);
			writeJson(parser.parse(), filePath);
		} else if (args.length == 1) {
			CharReader reader = new CharReader(args[0]);
			Lexer lexer = new Lexer(reader);
			Parser parser = new Parser(lexer);
			parser.parse();
			System.out.println("valid");
		} else if (args.length == 0) {
			String filePath = "/Users/huang/Desktop/country.json";
			CharReader reader = new CharReader(filePath);
			Lexer lexer = new Lexer(reader);
			Parser parser = new Parser(lexer);
			System.out.println(parser.parse());
//			writeJson(parser.parse(), filePath);
		} else {
			System.out.println("Arguments are invalid!\n");
			helpDocument();
		}
	}

	private static void helpDocument() {
		System.out.println("Usage: java -jar jsonCompiler.jar [options] sourceFile");
		System.out.println("Just show whether the file is valid without options");
		System.out.println("options:");
		System.out.println("\t-find <path>, to find the specified value and show its type,");
		System.out.println("\tthe format of <path> for example: \"/RECORDS[35]/countryname\"");
		System.out.println("\n\t-pretty, to give the Json file with better output format");
	}

	private static void writeJson(Object prettyJson, String path) {

		String[] pathArr = path.split("\\.");
		StringBuilder newPathBuilder = new StringBuilder();
		for (int i = 0; i < pathArr.length; i++) {
			if (i != pathArr.length - 1) {
				if (i == 0) {
					newPathBuilder.append(pathArr[i]);
				} else {
					newPathBuilder.append("." + pathArr[i]);
				}
			} else {
				newPathBuilder.append(".pretty.json");
			}
		}
		String newPath = newPathBuilder.toString();
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(newPath));
			writer.write(prettyJson.toString());
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
