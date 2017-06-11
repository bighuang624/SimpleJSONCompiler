package frontend;

import java.util.List;
import java.util.Map;

/**
 * @author huang 定义栈中元素的类型和值
 * 栈中元素包括对象、数组、键值对的键、键值对的值、单值
 */
public class StackElement {
	final String type;
	final Object value;

	private StackElement(String type, Object value) {
		this.type = type;
		this.value = value;
	}

	static StackElement newObject(Object obj) {
		return new StackElement(OBJECT, obj);
	}

	static StackElement newObject(Map<String, Object> map) {
		return new StackElement(OBJECT, map);
	}

	static StackElement newObjectKey(String key) {
		return new StackElement(OBJECT_KEY, key);
	}

	static StackElement newArray(List<Object> arr) {
		return new StackElement(ARRAY, arr);
	}

	static StackElement newSingle(Object obj) {
		return new StackElement(SINGLE, obj);
	}

	public String valueAsKey() {
		return "\"" + value + "\"";
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> valueAsObject() {
		return (Map<String, Object>) value;
	}

	@SuppressWarnings("unchecked")
	List<Object> valueAsArray() {
		return (List<Object>) value;
	}

	static final String OBJECT = "object";
	static final String OBJECT_KEY = "object_key";
	static final String ARRAY = "array";
	static final String SINGLE = "single";
}
