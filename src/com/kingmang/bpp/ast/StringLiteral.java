package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class StringLiteral extends Expression {
	public StringLiteral(Object string) {
		operands.add(string);
	}

	public Object evaluate(Context context) {
		return operands.get(0);
	}
}