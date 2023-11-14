package com.kingmang.bpp.ast;
import java.util.List;

import com.kingmang.bpp.Context;


public class NewExpression extends Expression {

	private static final String CLASSNAME_PREFIX = "jp.junkato.kotosaka.";

	public NewExpression(String class_name, List<Expression> argument_list) {

		Object[] arguments = new Object[argument_list.size()];
		for(int i = 0; i < argument_list.size(); i ++) arguments[i] = argument_list.get(i);

		operands.add(class_name);
		operands.add(arguments);
	}

	public Object evaluate(Context context) {
		String class_name = (String) operands.get(0);
		Object[] expressions = (Object[]) operands.get(1);

		// Prepare arguments and an array of their types.
		Object[] arguments = new Object[expressions.length];
		Class<?>[] params = new Class[arguments.length];
		for (int i = 0; i < arguments.length; i ++) {
			if (expressions[i] == null) {
				arguments[i] = null;
				params[i] = null;
			} else {
				arguments[i] = ((Expression) expressions[i]).evaluate((context));
				params[i] = arguments[i].getClass();
			}

			// TODO read MethodCall.evaluate(Context)
			if (arguments[i] instanceof Integer) {
				params[i] = int.class;
			}
		}

		try {
			final Object instance = Class.forName(CLASSNAME_PREFIX+class_name)
					.getConstructor(params)
					.newInstance(arguments);
			context.registerObject(instance);
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
