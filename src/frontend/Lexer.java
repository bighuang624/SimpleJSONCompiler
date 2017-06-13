package frontend;

import exception.ExceptionMessage;
import exception.LexException;
import frontend.util.Token;
import frontend.util.TokenType;

/**
 * @author huang 
 * 读取 token
 */
public class Lexer {
	private CharReader charReader;

	public Lexer(CharReader charReader) {
		this.charReader = charReader;
	}

	public boolean hasNext() {
		return charReader.peekChar() != CharReader.EOF;
	}

	public Token next() throws LexException {
		char ch = charReader.nextChar();
		for (;;) {
			if (!hasNext()) {
				return new Token(TokenType.EOF, "end_of_file", charReader.getCurrentLine(),
						charReader.getCurrentPosition());
			}
			if (!Character.isWhitespace(ch)) {
				break;
			}
			ch = charReader.nextChar();
		}
		// number，包括integer、float、scientific（分开）
		if (Character.isDigit(ch) || ch == '-') {
			final StringBuilder number = new StringBuilder();
			int offset = 0;
			if (ch == '-') {
				number.append(ch);
				ch = charReader.peekChar();
				if (Character.isDigit(ch)) {
					++offset;
					charReader.nextChar();
				} else {
					throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
							charReader.getCurrentPosition() - offset);
				}
			}
			number.append(ch);

			final int READ_NUMBER_INT_PART = 0;
			final int READ_NUMBER_FRA_PART = 1;
			final int READ_NUMBER_EXP_PART = 2;
			final int READ_NUMBER_END = 3;

			boolean hasFraPart = false;
			boolean hasExpPart = false;

			int status = READ_NUMBER_INT_PART;
			for (;;) {
				if (hasNext()) {
					ch = charReader.peekChar();
				} else {
					status = READ_NUMBER_END;
				}

				switch (status) {
				case READ_NUMBER_INT_PART:
					if (Character.isDigit(ch)) {
						number.append(ch);
						charReader.nextChar();
						++offset;
					} else if (ch == '.') {
						status = READ_NUMBER_FRA_PART;
						number.append(ch);
						charReader.nextChar();
						++offset;
					} else if (ch == 'e' || ch == 'E') {
						status = READ_NUMBER_EXP_PART;
						number.append(ch);
						charReader.nextChar();
						++offset;
						char symbolChar = charReader.peekChar();
						if (symbolChar == '+' || symbolChar == '-') {
							number.append(symbolChar);
							charReader.nextChar();
							++offset;
						}
					} else {
						if (Character.isAlphabetic(ch)) {
							throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
									charReader.getCurrentPosition() - offset);
						}
						status = READ_NUMBER_END;
					}
					continue;

				case READ_NUMBER_FRA_PART:
					if (Character.isDigit(ch)) {
						if (!hasFraPart) {
							hasFraPart = true;
						}
						number.append(ch);
						charReader.nextChar();
						++offset;
					} else if (ch == 'e' || ch == 'E') {
						status = READ_NUMBER_EXP_PART;
						number.append(ch);
						charReader.nextChar();
						++offset;
						char symbolChar = charReader.peekChar();
						if (symbolChar == '+' || symbolChar == '-') {
							number.append(symbolChar);
							charReader.nextChar();
							++offset;
						}
					} else {
						if (!hasFraPart) {
							throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
									charReader.getCurrentPosition() - offset);
						}
						if (Character.isAlphabetic(ch) || ch == '.') {
							throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
									charReader.getCurrentPosition() - offset);
						}
						status = READ_NUMBER_END;
					}
					continue;

				case READ_NUMBER_EXP_PART:
					if (Character.isDigit(ch)) {
						if (!hasExpPart) {
							hasExpPart = true;
						}
						number.append(ch);
						charReader.nextChar();
						++offset;
					} else {
						if (!hasExpPart) {
							throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
									charReader.getCurrentPosition() - offset);
						}
						if (Character.isAlphabetic(ch) || ch == '.' || ch == 'e' || ch == 'E') {
							throw new LexException(ExceptionMessage.ILLEGAL_NUMBER, charReader.getCurrentLine(),
									charReader.getCurrentPosition() - offset);
						}
						status = READ_NUMBER_END;
					}
					continue;

				case READ_NUMBER_END:
					if (hasExpPart) {
						return new Token(TokenType.SCIENTIFIC, number.toString(), charReader.getCurrentLine(),
								charReader.getCurrentPosition() - offset);
					} else if (hasFraPart) {
						return new Token(TokenType.FLOAT, number.toString(), charReader.getCurrentLine(),
								charReader.getCurrentPosition() - offset);
					} else {
						return new Token(TokenType.INTEGER, number.toString(), charReader.getCurrentLine(),
								charReader.getCurrentPosition() - offset);
					}
				}
			}

		}
		// String
		else if (ch == '\"') {
			StringBuilder string = new StringBuilder();
			int offset = 0;

			for (;;) {
				ch = charReader.nextChar();
				++offset;

				if (ch == '\\') {
					char nch = charReader.nextChar();
					switch (nch) {
					case 'b':
						string.append('\b');
						break;
					case 'f':
						string.append('\f');
						break;
					case 'n':
						string.append('\n');
						break;
					case 'r':
						string.append('\r');
						break;
					case 't':
						string.append('\t');
						break;
					case 'u':
						// read an unicode:
						int u = 0;
						for (int i = 0; i < 4; i++) {
							char uch = charReader.nextChar();
							if (uch >= '0' && uch <= '9') {
								u = (u << 4) + (uch - '0');
							} else if (uch >= 'a' && uch <= 'f') {
								u = (u << 4) + (uch - 'a') + 10;
							} else if (uch >= 'A' && uch <= 'F') {
								u = (u << 4) + (uch - 'A') + 10;
							} else {
								throw new LexException(ExceptionMessage.UNEXPECTED_CHAR + uch,
										charReader.getCurrentLine(), charReader.getCurrentPosition());
							}
						}
						break;
					case '\"':
						string.append('\"');
						break;
					case '\\':
						string.append('\\');
						break;
					case '/':
						string.append('/');
						break;
					default:
						throw new LexException(ExceptionMessage.UNEXPECTED_CHAR + ch, charReader.getCurrentLine(),
								charReader.getCurrentPosition());
					}
				} else if (ch == '\r' || ch == '\n') {
					throw new LexException(ExceptionMessage.EXPECTED_BACKQUOTE, charReader.getCurrentLine(),
							charReader.getCurrentPosition());
				} else if (ch == '\"') {
					break;
				} else {
					string.append(ch);
				}
			}

			return new Token(TokenType.STRING, string.toString(), charReader.getCurrentLine(),
					charReader.getCurrentPosition() - offset);
		}
		// true
		else if (ch == 't') {
			int tokenOffset = 3;
			String expected = "rue";
			for (int i = 0; i < tokenOffset; i++) {
				ch = charReader.nextChar();
				if (ch != expected.charAt(i)) {
					throw new LexException(ExceptionMessage.UNEXPECTED_CHAR + ch, charReader.getCurrentLine(),
							charReader.getCurrentPosition());
				}
			}

			return new Token(TokenType.TRUE, "true", charReader.getCurrentLine(),
					charReader.getCurrentPosition() - tokenOffset);
		}
		// false
		else if (ch == 'f') {
			int tokenOffset = 4;
			String expected = "alse";
			for (int i = 0; i < tokenOffset; i++) {
				ch = charReader.nextChar();
				if (ch != expected.charAt(i)) {
					throw new LexException(ExceptionMessage.UNEXPECTED_CHAR + ch, charReader.getCurrentLine(),
							charReader.getCurrentPosition());
				}
			}

			return new Token(TokenType.FALSE, "false", charReader.getCurrentLine(),
					charReader.getCurrentPosition() - tokenOffset);
		}
		// null
		else if (ch == 'n') {
			int tokenOffset = 3;
			String expected = "ull";
			for (int i = 0; i < tokenOffset; i++) {
				ch = charReader.nextChar();
				if (ch != expected.charAt(i)) {
					throw new LexException(ExceptionMessage.UNEXPECTED_CHAR + ch, charReader.getCurrentLine(),
							charReader.getCurrentPosition());
				}
			}

			return new Token(TokenType.NULL, "null", charReader.getCurrentLine(),
					charReader.getCurrentPosition() - tokenOffset);
		}
		// [
		else if (ch == '[') {
			return new Token(TokenType.LEFT_BRACKET, "[", charReader.getCurrentLine(), charReader.getCurrentPosition());
		}
		// ]
		else if (ch == ']') {
			return new Token(TokenType.RIGHT_BRACKET, "]", charReader.getCurrentLine(),
					charReader.getCurrentPosition());
		}
		// {
		else if (ch == '{') {
			return new Token(TokenType.LEFT_BRACE, "{", charReader.getCurrentLine(), charReader.getCurrentPosition());
		}
		// }
		else if (ch == '}') {
			return new Token(TokenType.RIGHT_BRACE, "}", charReader.getCurrentLine(), charReader.getCurrentPosition());
		}
		// :
		else if (ch == ':') {
			return new Token(TokenType.COLON, ":", charReader.getCurrentLine(), charReader.getCurrentPosition());
		}
		// ,
		else if (ch == ',') {
			return new Token(TokenType.COMMA, ",", charReader.getCurrentLine(), charReader.getCurrentPosition());
		}
		// 报错
		else {
			throw new LexException(ExceptionMessage.ILLEGAL_TOKEN, charReader.getCurrentLine(),
					charReader.getCurrentPosition());
		}
	}
}
