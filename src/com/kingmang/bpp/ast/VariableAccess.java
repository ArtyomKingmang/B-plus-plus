package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;
import com.kingmang.bpp.Variable;

public class VariableAccess extends Expression {
	public VariableAccess(String variable_name) {
		operands.add(variable_name);
	}

	public Object evaluate(Context context) {
		final String name = (String) operands.get(0);
		final Variable variable = context.getVariable(name);

		if (variable == null)
		{
			return context.getFunction(name);
		}
		return variable.value;
	}
}
