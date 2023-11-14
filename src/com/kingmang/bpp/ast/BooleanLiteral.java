package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class BooleanLiteral extends Expression {
	public BooleanLiteral(Boolean bool) {
		operands.add(bool);
	}

	public Boolean evaluate(Context context) {
		return (Boolean) operands.get(0);
	}
}