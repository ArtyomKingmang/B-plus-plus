package com.kingmang.bpp.ast;
import java.util.List;

import com.kingmang.bpp.Context;
import com.kingmang.bpp.Function;
import com.kingmang.bpp.Variable;


public class FunctionCall extends Expression {
	public FunctionCall(String functionName, List<Expression> argumentList){
		operands.add(functionName);
		operands.add(argumentList);
	}

	@SuppressWarnings("unchecked")
	public Object evaluate(Context context) {
		String functionName = (String) operands.get(0);
		List<Expression> argumentList = (List<Expression>) operands.get(1);

		Function function = context.getFunction(functionName);


		if (function == null)
		{

			return null;
		}

		Context localContext = context.createLocalContext(function.instructions);

		Object[] arguments = new Object[argumentList.size()];
		for(int i = 0; i < argumentList.size(); i ++) {
			Expression expression = (Expression) argumentList.get(i);
			if (expression == null) arguments[i] = null;
			else arguments[i] = expression.evaluate(context);
		}

		for(int i = 0; i < function.arguments.size(); i++) {
			String name = function.arguments.get(i).toString();
			if (arguments[i] != null) {
				if (arguments[i] instanceof Function) {
					Function func = (Function) arguments[i];
					localContext.addFunction(new Function(name, func.arguments, func.instructions));
					continue;
				}
			}
			localContext.addVariable(new Variable(name, arguments[i]));
		}


		return localContext.run();
	}
}
