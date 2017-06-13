package frontend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author huang 
 * 输出 json 文件
 */
public class JsonWriter {

	public static void writeJson(Object prettyJson, String path) {

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
