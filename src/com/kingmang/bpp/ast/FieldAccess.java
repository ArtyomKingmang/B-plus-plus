package com.kingmang.bpp.ast;
import java.lang.reflect.*;

import com.kingmang.bpp.Context;

public class FieldAccess extends Expression {
	public FieldAccess(Expression expression, String field_name) {
		operands.add(expression);
		operands.add(field_name);
	}

	public Object evaluate(Context context) {
		final Expression expression = (Expression) operands.get(0);
		final String field_name = (String) operands.get(1);
		final Object object = expression.evaluate(context);

		try {

			final Class<?> cls = object.getClass();

			final Field field = cls.getField(field_name);

			final Object ret = field.get(object);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
