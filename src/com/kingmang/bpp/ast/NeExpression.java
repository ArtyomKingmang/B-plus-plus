package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class NeExpression extends BoolExpression {
	public NeExpression(Object op1, Object op2) { super(op1, op2); }

	public Boolean evaluate(Context context) {
		super.evaluate(context);
		return Boolean.valueOf(!op1.equals(op2));
	}
}
