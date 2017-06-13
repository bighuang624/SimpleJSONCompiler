package frontend;

import java.util.List;
import java.util.Map;

import frontend.util.Token;

/**
 * @author huang 
 * 定义栈中元素的类型和值 栈中元素包括对象、数组、键值对的键、值
 */
public class StackElement {
	final String type;
	final Object value;

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	private StackElement(String type, Object value) {
		this.type = type;
		this.value = value;
	}

	public static StackElement newObject(Map<String, Object> map) {
		return new StackElement(OBJECT, map);
	}

	public static StackElement newKey(Token token) {
		return new StackElement(KEY, token.getValue());
	}

	public static StackElement newArray(List<Object> arr) {
		return new StackElement(ARRAY, arr);
	}

	public static StackElement newSingle(Object obj) {
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
	public List<Object> valueAsArray() {
		return (List<Object>) value;
	}

	public StackElement get(int childNum) {

		Object element = valueAsArray().get(childNum);
		if (element.equals(null)) {
			return new StackElement(NULL, null);
		}

		String className = element.getClass().toString();
		if (className.equals("class frontend.util.LinkedMap")) {
			return new StackElement(OBJECT, element);
		} else if (className.equals("class frontend.util.ArrList")) {
			return new StackElement(ARRAY, element);
		} else {
			return new StackElement(SINGLE, element);
		}
	}

	public StackElement get(String field) {

		String keyField = "\"" + field + "\"";
		Object element = valueAsObject().get(keyField);

		if (null == element) {
			return new StackElement(NULL, null);
		}

		String className = element.getClass().toString();
		if (className.equals("class frontend.util.LinkedMap")) {
			return new StackElement(OBJECT, element);
		} else if (className.equals("class frontend.util.ArrList")) {
			return new StackElement(ARRAY, element);
		} else {
			return new StackElement(SINGLE, element);
		}

	}

	@Override
	public String toString() {
		if (null == value) {
			return "null";
		}
		return value.toString();
	}

	public static final String OBJECT = "object";
	public static final String KEY = "key";
	public static final String ARRAY = "array";
	public static final String SINGLE = "single";
	public static final String NULL = "null";
}
