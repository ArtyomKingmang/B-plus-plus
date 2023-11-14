package com.kingmang.bpp;

import java.awt.Color;

public class Token {

	public Object value;
	private int tag;

	private int index;
	private int length;

	public Token(Float number, int index, int length) {
		value = number;
		tag = Tag.tagNumber;
		initialize(index, length);
	}

	public Token(String str, int index, int length) {
		value = str;
		tag = Tag.tagString;
		initialize(index, length);
	}

	public Token(Character c, int index, int length) {
		value = c;
		tag = Tag.tagCharacter;
		initialize(index, length);
	}

	public Token(char c, int index, int length) {
		value = c;
		tag = Tag.tagSymbol;
		initialize(index, length);
	}

	public Token(int type, Object obj, int index, int length) {
		value = obj;
		tag = type;
		initialize(index, length);
	}

	public Token(int type, int index, int length) {
		value = null;
		tag = type;
		initialize(index, length);
	}

	private void initialize(int index, int length) {
		this.index = index;
		this.length = length;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return tag;
	}

	public Color getColor() {
		return Tag.toColor(getTag());
	}

	public int getIndex() {
		return index;
	}

	public int getLength() {
		return length;
	}

	public boolean isOperator() {
		if (tag != Tag.tagSymbol)
			return false;
		char c = ((Character) value).charValue();
		switch (c) {
		case '=':
		case '!':
		case '<':
		case '>':
		case '*':
		case '/':
		case '^':
		case '%':
		case '+':
		case '-':
			return true;
		default:
			break;
		}
		return false;
	}

	public boolean equals(Token token) {
		return
			tag == token.tag &&
			value.equals(token.value);
	}

	public boolean equals(char character) {
		return
			tag == Tag.tagSymbol &&
			charValue() == character;
	}

	public char charValue() {
		if (tag == Tag.tagSymbol)
			return ((Character) value).charValue();
		return 0;
	}

	public String toString() {
		if (tag == Tag.tagName || tag == Tag.tagSystem)
			return "(" + Tag.toName(tag) + " " + value.toString() + " "
					+ Integer.toHexString(this.hashCode()) + ")";
		if (tag == Tag.tagSymbol)
			return "(" + Tag.toName(tag) + " '" + value.toString() + "')";
		if (value != null)
			return "(" + Tag.toName(tag) + " " + value.toString() + ")";
		else
			return "(" + Tag.toName(tag) + ")";
	}

	public void print() {
		System.out.print("Tag = " + tag + "(" + Tag.toName(tag) + "), value = "
				+ value);
	}

}
