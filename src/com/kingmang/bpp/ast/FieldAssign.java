package com.kingmang.bpp.ast;
import java.lang.reflect.*;

import com.kingmang.bpp.Context;

public class FieldAssign extends Expression {
	public FieldAssign(Expression expression, String field_name, Expression assignment) {
		operands.add(expression);
		operands.add(field_name);
		operands.add(assignment);
	}

	public Object evaluate(Context context) {
		final Expression expression = (Expression) operands.get(0);
		final String field_name = (String) operands.get(1);
		final Expression assignment = (Expression) operands.get(2);

		Object object = expression.evaluate(context);

		try {

			final Class<?> cls = object.getClass();

			final Field field = cls.getField(field_name);

			final Object ret = assignment.evaluate(context);
			field.set(object, ret);
			return ret;
		} catch (Exception e) {
			System.err.println("Failed to set member variable.");
		}
		return null;
	}
}
