package com.kingmang.bpp.parser;

import com.kingmang.bpp.Char;
import com.kingmang.bpp.Tag;
import com.kingmang.bpp.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Reader {

	private List<Char> characters;

	private int index;
	private int length;

	private Char character;
	private char ch;

	public Queue<Token> read(List<Char> characters) {
		LinkedList<Token> tokens = new LinkedList<Token>();
		read(characters, tokens, 0);
		return tokens;
	}

	public void read(List<Char> characters, LinkedList<Token> tokens,
			int i) {
		this.characters = characters;
		index = i;
		length = characters.size();
		while (index < length) {
			getChar();

			if (Character.isWhitespace(ch))
				continue;
			else if (ch == '/' && makeComment())
				continue;
			else if (Character.isDigit(ch))
				tokens.add(makeNumber());
			else if (ch == '"')
				tokens.add(makeString());
			else if (isSymbol(ch)) {
				final Token token = new Token(ch,
						index - 1, 1);
				character.setParent(token);
				tokens.add(token);
			}
			else
				tokens.add(makeName());
		}
		tokens.add(new Token(Tag.tagEnd, 0, length, 0));
	}

	private boolean isSymbol(char ch) {
		return (ch < 'A' || ch == '{' || ch == '}' || ch == '[' || ch == ']');
	}

	private boolean makeComment() {
		char nch = nextChar();
		if (nch == '/')
			skipLF();
		else if (nch == '*')
			skipComment();
		else
			return false;
		return true;
	}

	private void skipLF() {
		getChar();
		while (ch != '\n' && ch != '\0')
			getChar();
	}

	private void skipComment() {
		getChar();
		getChar();
		while (true) {
			if (ch == '*' && nextChar() == '/')
				break;
			getChar();
		}
		index += 2;
	}

	private Token makeNumber() {
		final int startIndex = index - 1;
		int tokenLength = 0;
		StringBuffer str = new StringBuffer();
		for (; index < length; getChar()) {
			if (!Character.isDigit(nextChar())
					&& nextChar() != '.')
				break;
			str.append(ch);
			tokenLength++;
		}
		str.append(ch);
		tokenLength++;
		final Token token = new Token(Float.valueOf(str.toString()),
				startIndex, tokenLength);
		setCharactersParentToken(token, startIndex);
		return token;
	}

	private Token makeName() {
		final int startIndex = index - 1;
		int tokenLength = 1;
		StringBuffer str = new StringBuffer().append(ch);
		while (index < length) {
			final char nextCh = nextChar();
			if (!(Character.isLetter(nextCh) || nextCh == '_' || Character
					.isDigit(nextCh)))
				break;
			getChar();
			str.append(ch);
			tokenLength++;
		}
		final String name = str.toString();
		Token token;
		if (Parser.reservedWords.containsKey(name)) {
			token = new Token(Tag.tagSystem, Parser.reservedWords.get(name),
					startIndex, tokenLength);
		} else {
			token = new Token(Tag.tagName, name, startIndex,
					tokenLength);
		}
		setCharactersParentToken(token, startIndex);
		return token;
	}

	private Token makeString() {
		final int startIndex = index - 1;
		int tokenLength = 1;
		StringBuffer str = new StringBuffer();
		while (index < length) {
			getChar();
			if (ch == '"')
				break;
			if (ch == '\\') {
				getChar();
			}
			str.append(ch);
			tokenLength++;
		}
		final Token token = new Token(Tag.tagString, str.toString(),
				startIndex, tokenLength);
		setCharactersParentToken(token, startIndex);
		return token;
	}

	private void setCharactersParentToken(Token token, int startIndex) {
		for (int i = startIndex; i < index; i++)
			characters.get(i).setParent(token);
	}

	private void getChar() {
		character = characters.get(index++);
		ch = character.getCharacter();
	}

	private char nextChar() {
		if (index < 0 ||
				index >= characters.size())
		{
			return 0;
		}
		return characters.get(index).getCharacter();
	}
}
