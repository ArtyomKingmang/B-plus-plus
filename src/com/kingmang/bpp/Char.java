package com.kingmang.bpp;

public class Char {
	private Token parent;
	private char character;

	public Char() {
		// Do nothing.
	}

	public Char(char character) {
		setCharacter(character);
	}

	public void setParent(Token parent) {
		this.parent = parent;
	}

	public Token getParent() {
		return parent;
	}

	public void setCharacter(char character)
	{
		this.character = character;
	}

	public char getCharacter()
	{
		return character;
	}

	@Override
	public String toString()
	{
		return String.valueOf(character);
	}
}
