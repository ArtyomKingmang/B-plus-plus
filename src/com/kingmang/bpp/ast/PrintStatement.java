package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;


public class PrintStatement extends Statement {
	public PrintStatement(Expression expression){
		operands.add(expression);
	}

	public Object evaluate(Context context){
		final Expression expression = (Expression) operands.get(0);
		final Object result = expression.evaluate(context);
		if (result != null)
		{
			final String resultString = result.toString();
			System.out.println(resultString);
		}
		return null;
	}
}
