package frontend.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author huang 
 * 继承 LinkedHashMap 以重写其 toString 方法
 */

public class LinkedMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private final int tabsNum = Counter.getTabsNum();

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder();
		Iterator<Entry<String, Object>> iter = entrySet().iterator();
		value.append("{\n");
		addTabs(value);
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			value.append(entry.getKey() + ": " + entry.getValue() + ",\n");
			addTabs(value);
		}
		value.delete(value.length() - 2 - tabsNum, value.length() - 1 - tabsNum);
		reduceOneTab(value);
		value.append("}");
		return value.toString();
	}

	private void addTabs(StringBuilder value) {
		for (int j = 0; j < tabsNum; j++) {
			value.append("\t");
		}
	}

	private void reduceOneTab(StringBuilder value) {
		value.delete(value.length() - 1, value.length());
	}
}
