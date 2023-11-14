package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class PowExpression extends CalcExpression {
	public PowExpression(Object op1, Object op2) { super(op1, op2); }

	public Object evaluate(Context context) {
		super.evaluate(context);
		if (prepareCalc()) {
			if (op1f != null && op2f != null)
				return Float.valueOf((float) Math.pow(op1f, op2f));
		}
		return null;
	}
}
