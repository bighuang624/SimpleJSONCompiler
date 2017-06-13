package frontend.util;

/**
 * @author huang 
 * 搜索字段
 */
public class SearchField {
	private String type;
	private String key;
	private int childNum;

	public SearchField() {
	}

	public SearchField(String type, String key) {
		this.type = type;
		this.key = key;
	}

	public SearchField(String type, String key, int childNum) {
		this.type = type;
		this.key = key;
		this.childNum = childNum;
	}

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public int getChildNum() {
		return childNum;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	@Override
	public String toString() {
		return type + key + childNum;
	}

	public static final String OBJECT = "OBJECT";
	public static final String ARRAY = "ARRAY";
	public static final String KEY = "KEY";
}
