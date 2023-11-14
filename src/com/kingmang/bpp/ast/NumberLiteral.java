package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class NumberLiteral extends Expression {
	public NumberLiteral(Object integer) {
		operands.add(integer);
	}

	public Object evaluate(Context context) {
		return (Float) operands.get(0);
	}
}