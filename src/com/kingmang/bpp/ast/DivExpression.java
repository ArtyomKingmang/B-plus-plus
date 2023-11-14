package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class DivExpression extends CalcExpression {
	public DivExpression(Object op1, Object op2) { super(op1, op2); }

	public Object evaluate(Context context) {
		super.evaluate(context);
		if (prepareCalc()) {
			if (op1v != null && op2v != null)
				return op1v.div(op2v);
			if (op1v != null && op2f != Float.NaN)
				return op1v.div(op2f);
			if (op1f != null && op2f != null)
				return Float.valueOf(op1f / op2f);
		}
		return null;
	}
}
