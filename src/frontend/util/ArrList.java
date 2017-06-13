package frontend.util;

import java.util.ArrayList;

/**
 * @author huang 
 * 继承 ArrayList 以重写其 toString 方法
 */

public class ArrList extends ArrayList<Object> {

	private static final long serialVersionUID = 1L;

	private final int tabsNum = Counter.getTabsNum();

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder();
		value.append("[\n");
		addTabs(value);
		for (int i = 0; i < size(); i++) {
			if (i == size() - 1) {
				value.append(get(i) + "\n");
				addTabs(value);
			} else {
				value.append(get(i) + ",\n");
				addTabs(value);
			}
		}
		reduceTabs(value);
		value.append("]");
		return value.toString();
	}

	private void addTabs(StringBuilder value) {
		for (int j = 0; j < tabsNum; j++) {
			value.append("\t");
		}
	}

	private void reduceTabs(StringBuilder value) {
		value.delete(value.length() - 1, value.length());
	}
}
