package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class SubExpression extends CalcExpression {
	public SubExpression(Object op1, Object op2) { super(op1, op2); }

	public Object evaluate(Context context) {
		super.evaluate(context);
		if (prepareCalc()) {
			if (op1m != null && op2m != null)
				return op1m.sub(op2m);
			if (op1v != null && op2v != null)
				return op1v.sub(op2v);
			if (op1f != null && op2f != null)
				return Float.valueOf(op1f - op2f);
		}
		return null;
	}
}
