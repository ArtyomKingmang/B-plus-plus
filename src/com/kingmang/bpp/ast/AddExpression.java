package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class AddExpression extends CalcExpression {
	public AddExpression(Object op1, Object op2) { super(op1, op2); }

	public Object evaluate(Context context) {
		super.evaluate(context);
		if (prepareCalc()) {
			if (op1m != null && op2m != null)
				return op1m.sum(op2m);
			if (op1v != null && op2v != null)
				return op1v.sum(op2v);
			if (op1f != null && op2f != null)
				return Float.valueOf(op1f + op2f);
		}
		String text = "";
		if (op1 != null) text += op1.toString();
		if (op2 != null) text += op2.toString();
		return text;
	}
}
