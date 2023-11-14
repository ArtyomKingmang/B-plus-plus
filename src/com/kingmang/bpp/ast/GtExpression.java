package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class GtExpression extends BoolExpression {
	public GtExpression(Object op1, Object op2) { super(op1, op2); }

	public Boolean evaluate(Context context) {
		super.evaluate(context);
		if (!prepareCalc()) return null;
		if (op1f == null && op2f == null) return null;
		return Boolean.valueOf(op1f > op2f);
	}
}
