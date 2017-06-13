package frontend;

import java.util.LinkedList;

import exception.LexException;
import exception.SyntaxException;
import exception.stackException.EmptyStackException;
import exception.stackException.StackOverflowException;
import frontend.util.SearchField;

/**
 * @author huang 
 * 根据路径查找到字段对应的值
 */
public class Searcher {

	private Parser parser;

	public Searcher(Parser parser) {
		this.parser = parser;
	}

	public LinkedList<SearchField> getSearchPathList(String searchPath) {
		String[] pathArr = searchPath.split("/");
		LinkedList<SearchField> list = new LinkedList<>();

		for (int i = 0; i < pathArr.length; i++) {

			if (pathArr[i].contains("[") && pathArr[i].contains("]")) { // 此字段为数组中的值
				String field = pathArr[i];
				int leftBracketPlace = field.indexOf('[');
				int rightBracketPlace = field.indexOf(']');

				String key = field.substring(0, leftBracketPlace);
				int childNum = Integer.parseInt(field.substring(leftBracketPlace + 1, rightBracketPlace));
				list.add(new SearchField(SearchField.ARRAY, key, childNum));

				while (rightBracketPlace != field.length() - 1) {
					field = field.substring(rightBracketPlace + 1, field.length());
					leftBracketPlace = field.indexOf('[');
					rightBracketPlace = field.indexOf(']');

					key = field.substring(0, leftBracketPlace);
					childNum = Integer.parseInt(field.substring(leftBracketPlace + 1, rightBracketPlace));
					list.add(new SearchField(SearchField.ARRAY, key, childNum));
				}
			} else if (i == 0) {
				list.add(new SearchField(SearchField.OBJECT, pathArr[i]));
			} else {
				list.add(new SearchField(SearchField.KEY, pathArr[i])); // 也有可能是对象或数组，但是把它们当作单值
			}
		}

		return list;
	}

	public StackElement search(String searchPath)
			throws LexException, SyntaxException, StackOverflowException, EmptyStackException {

		LinkedList<SearchField> list = getSearchPathList(searchPath);
		StackElement result = parser.parse();

		for (int i = 0; i < list.size(); i++) {
			if (null == result.value) {
				return null;
			}
			SearchField nextField = list.get(i);
			switch (nextField.getType()) {
			case SearchField.ARRAY:
				if (nextField.getKey().equals("")) {
					result = result.get(nextField.getChildNum());
				} else {
					result = result.get(nextField.getKey());
					if (null == result.value) {
						return null;
					}
					result = result.get(nextField.getChildNum());
				}
				break;
			case SearchField.KEY:
				result = result.get(nextField.getKey());
				break;
			default:
				break;
			}
		}
		return result;
	}
}
