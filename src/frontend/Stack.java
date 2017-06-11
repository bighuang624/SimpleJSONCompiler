package frontend;

import exception.ExceptionMessage;
import exception.SyntaxException;
import exception.stackException.EmptyStackException;
import exception.stackException.StackOverflowException;

/**
 * @author huang 
 * 自己封装一个栈以实现一些方法
 */
public class Stack {
	final int SIZE;
	final StackElement[] array;
	int position = 0;

	public Stack() {
		this.SIZE = 100;
		this.array = new StackElement[this.SIZE];
	}

	boolean isEmpty() {
		if (position == 0) {
			return true;
		}
		return false;
	}

	void push(StackElement element) throws StackOverflowException {
		if (position == SIZE) {
			throw new StackOverflowException(ExceptionMessage.STACKOVERFLOW);
		}
		array[position] = element;
		position++;
	}

	StackElement pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException(ExceptionMessage.EMPTY_STACK);
		}
		position--;
		return array[position];
	}

	StackElement pop(String type) throws SyntaxException, EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException(ExceptionMessage.EMPTY_STACK);
		}
		position--;
		StackElement obj = array[position];
		if (obj.type == type) {
			return obj;
		}
		throw new SyntaxException(ExceptionMessage.UNMATCHED_OBORARR);
	}

	// Class<?> getTopValueClass() {
	// StackElement obj = array[position - 1];
	// return obj.value.getClass();
	// }

	String getTopValueType() {
		return array[position - 1].type;
	}

	StackElement peek(String type) throws EmptyStackException, SyntaxException {
		if (isEmpty()) {
			throw new EmptyStackException(ExceptionMessage.EMPTY_STACK);
		}
		StackElement obj = array[position - 1];
		if (obj.type == type) {
			return obj;
		}
		throw new SyntaxException(ExceptionMessage.UNMATCHED_OBORARR);
	}

}
