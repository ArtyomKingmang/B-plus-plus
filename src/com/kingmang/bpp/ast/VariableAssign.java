package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;
import com.kingmang.bpp.Variable;

public class VariableAssign extends Expression {
	public VariableAssign(String variableName, Expression assignment) {
		operands.add(variableName);
		operands.add(assignment);
	}

	public Object evaluate(Context context) {
		String variableName = (String) operands.get(0);
		Expression assignment = (Expression) operands.get(1);

		Variable variable = context.getVariable(variableName);


		if (variable == null)
			variable = context.createVariable(variableName);
		variable.value = assignment.evaluate(context);

		return variable.value;
	}
}
