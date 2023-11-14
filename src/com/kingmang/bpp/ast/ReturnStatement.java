package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;

public class ReturnStatement extends Statement {
	public ReturnStatement(Expression expression) {
		operands.add(expression);
	}

	public Object evaluate(Context context) {
		Expression expression = (Expression) operands.get(0);
		return expression.evaluate(context);
	}

}
