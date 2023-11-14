package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class IntegerLiteral extends Expression {
	public IntegerLiteral(Object integer) {
		operands.add(integer);
	}

	public Object evaluate(Context context) {
		return (Integer) operands.get(0);
	}
}