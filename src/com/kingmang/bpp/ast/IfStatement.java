package com.kingmang.bpp.ast;
import java.util.List;

import com.kingmang.bpp.Context;

public class IfStatement extends Statement {
	public IfStatement(Expression expr, List<Evaluator> instructions) {
		operands.add(expr);
		operands.add(instructions);
	}
	public IfStatement(Expression expr, List<Evaluator> instructions, List<Evaluator> instructions_else) {
		operands.add(expr);
		operands.add(instructions);
		operands.add(instructions_else);
	}
	@SuppressWarnings("unchecked")
	public Object evaluate(Context context) {
		Expression expr = (Expression) operands.get(0);
		Context localContext;


		if((Boolean) expr.evaluate(context))
			localContext = context.createLocalContext((List<Evaluator>) operands.get(1));

		else if (operands.size() > 2)
			localContext = context.createLocalContext((List<Evaluator>) operands.get(2));

		else return null;

		localContext.run();
		return null;
	}
}
