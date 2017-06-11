package frontend;

import exception.ExceptionMessage;
import exception.LexException;
import exception.SyntaxException;
import exception.stackException.EmptyStackException;
import exception.stackException.StackOverflowException;
import frontend.util.ArrList;
import frontend.util.LinkedMap;
import frontend.util.TabsCounter;
import frontend.util.Token;

/**
 * @author huang
 *
 */
public class Parser {
	private Token cursor;
	private Lexer lexer;
	private Stack stack;
	private int status;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	private boolean hasStatus(int expectedStatus) {
		return ((status & expectedStatus) > 0); // 按位与
	}

	public Object parse() throws LexException, SyntaxException, StackOverflowException, EmptyStackException {
		stack = new Stack();
		status = EXPECT_BEGIN_OBJECT | EXPECT_BEGIN_ARRAY | EXPECT_SINGLE_VALUE;
		for (;;) {
			cursor = lexer.next();
			switch (cursor.getType()) {
			
			case LEFT_BRACE:  // 开始对象
				if (hasStatus(EXPECT_BEGIN_OBJECT)) {
					TabsCounter.addTabs();
					stack.push(StackElement.newObject(new LinkedMap()));    //  注意这里的数据结构
					status = EXPECT_BEGIN_OBJECT | EXPECT_END_OBJECT | EXPECT_OBJECT_KEY;
					continue;
				} else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_TOKEN, cursor.getLine(),
							cursor.getPosition());
				}
				
			case RIGHT_BRACE:  // 结束对象
				if (hasStatus(EXPECT_END_OBJECT)) {
					TabsCounter.reduceTabs();
                    StackElement object = stack.pop(StackElement.OBJECT);
                    if(stack.isEmpty()){    // root object
                    	stack.push(object);
                    	status = EXPECT_END_FILE;
                    	continue;
                    }
                    String type = stack.getTopValueType();
                    switch (type) {
					case StackElement.OBJECT_KEY:
						String key = stack.pop(StackElement.OBJECT_KEY).valueAsKey();
						stack.peek(StackElement.OBJECT).valueAsObject().put(key, object.value);
                        status = EXPECT_COMMA | EXPECT_END_OBJECT;
						continue;
					case StackElement.ARRAY:
						stack.peek(StackElement.ARRAY).valueAsArray().add(object.value);
                        status = EXPECT_COMMA | EXPECT_END_ARRAY;
                        continue;
					}
				} else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_TOKEN, cursor.getLine(),
							cursor.getPosition());
				}
				
			case LEFT_BRACKET:  // 开始数组
				if (hasStatus(EXPECT_BEGIN_ARRAY)) {
					TabsCounter.addTabs();
					stack.push(StackElement.newArray(new ArrList()));
					status = EXPECT_ARRAY_VALUE | EXPECT_BEGIN_OBJECT | EXPECT_BEGIN_ARRAY | EXPECT_END_ARRAY;
					continue;
				} else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_TOKEN, cursor.getLine(),
							cursor.getPosition());
				}
			case RIGHT_BRACKET:  // 结束数组
				if (hasStatus(EXPECT_END_ARRAY)) {
					StackElement array = stack.pop(StackElement.ARRAY);
					TabsCounter.reduceTabs();
                    if (stack.isEmpty()) {
                        stack.push(array);
                        status = EXPECT_END_FILE;
                        continue;
                    }
                    String type = stack.getTopValueType();
                    switch (type) {
					case StackElement.OBJECT_KEY:
                        String key = stack.pop(StackElement.OBJECT_KEY).valueAsKey();
                        stack.peek(StackElement.OBJECT).valueAsObject().put(key, array.value);
                        status = EXPECT_COMMA | EXPECT_END_OBJECT;
                        continue;
					case StackElement.ARRAY:
                        stack.peek(StackElement.ARRAY).valueAsArray().add(array.value);
                        status = EXPECT_COMMA | EXPECT_END_ARRAY;
                        continue;
					}
				} else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_TOKEN, cursor.getLine(),
							cursor.getPosition());
				}
				
			case COMMA:  // 逗号
				if(hasStatus(EXPECT_COMMA)){
					if(hasStatus(EXPECT_END_OBJECT)){
						status = EXPECT_OBJECT_KEY;
						continue;
					}
					else if(hasStatus(EXPECT_END_ARRAY)){
						status = EXPECT_ARRAY_VALUE | EXPECT_BEGIN_OBJECT | EXPECT_BEGIN_ARRAY;
						continue;
					}
				} else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_COMMA, cursor.getLine(),
							cursor.getPosition());
                }
			case COLON:  // 冒号
                if(status == EXPECT_COLON){
                	status = EXPECT_OBJECT_VALUE | EXPECT_BEGIN_OBJECT | EXPECT_BEGIN_ARRAY;
                	continue;
                } else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_COLON, cursor.getLine(),
							cursor.getPosition());
                }
			case STRING:
				if(hasStatus(EXPECT_SINGLE_VALUE)){
					stack.push(StackElement.newSingle(cursor));
					status = EXPECT_END_FILE;
					continue;
				}
				if(hasStatus(EXPECT_OBJECT_KEY)){
					stack.push(StackElement.newObjectKey(cursor.getValue()));
					status = EXPECT_COLON;
					continue;
				}
				if(hasStatus(EXPECT_OBJECT_VALUE)){
					String key = stack.pop(StackElement.OBJECT_KEY).valueAsKey();
					stack.peek(StackElement.OBJECT).valueAsObject().put(key, cursor);
					status = EXPECT_COMMA | EXPECT_END_OBJECT;
					continue;
				}
				if(hasStatus(EXPECT_ARRAY_VALUE)){
					stack.peek(StackElement.ARRAY).valueAsArray().add(cursor);
					status = EXPECT_COMMA | EXPECT_END_ARRAY;
                    continue;
				}
				
				throw new SyntaxException(ExceptionMessage.UNEXPECTED_STRING, cursor.getLine(),
						cursor.getPosition());
				
			case TRUE:
			case FALSE:
				
			case INTEGER:
			case FLOAT:
			case SCIENTIFIC:
				
			case NULL:
				
				if(hasStatus(EXPECT_SINGLE_VALUE)){
					stack.push(StackElement.newSingle(cursor));
					status = EXPECT_END_FILE;
					continue;
				}
				if(hasStatus(EXPECT_OBJECT_VALUE)){
					String key = stack.pop(StackElement.OBJECT_KEY).valueAsKey();
					stack.peek(StackElement.OBJECT).valueAsObject().put(key, cursor);
					status = EXPECT_COMMA | EXPECT_END_OBJECT;
					continue;
				}
				if(hasStatus(EXPECT_ARRAY_VALUE)){
					stack.peek(StackElement.ARRAY).valueAsArray().add(cursor);
					status = EXPECT_COMMA | EXPECT_END_ARRAY;
                    continue;
				}
				throw new SyntaxException(ExceptionMessage.UNEXPECTED_TOKEN, cursor.getLine(),
						cursor.getPosition());
		
			case EOF:  // 文件结束
			    if(hasStatus(EXPECT_END_FILE)){
			    	StackElement v = stack.pop();
			    	if(stack.isEmpty()){
			    		return v.value;
			    	}
			    } else {
					throw new SyntaxException(ExceptionMessage.UNEXPECTED_EOF, cursor.getLine(),
							cursor.getPosition());
			    }
			    
			default:
				break;
			}
		}
	}

	static final int EXPECT_END_FILE = 0x0002;
	static final int EXPECT_BEGIN_OBJECT = 0x0004;
	static final int EXPECT_END_OBJECT = 0x0008;
	static final int EXPECT_OBJECT_KEY = 0x0010;
	static final int EXPECT_OBJECT_VALUE = 0x0020;
	static final int EXPECT_COLON = 0x0040;
	static final int EXPECT_COMMA = 0x0080;
	static final int EXPECT_BEGIN_ARRAY = 0x0100;
	static final int EXPECT_END_ARRAY = 0x0200;
	static final int EXPECT_ARRAY_VALUE = 0x0400;
	static final int EXPECT_SINGLE_VALUE = 0x0800;

}
