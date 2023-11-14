package com.kingmang.bpp.ast;
import java.util.List;
import java.lang.reflect.*;

import com.kingmang.bpp.Context;

public class MethodCall extends Expression {
	public MethodCall(Expression expression, String methodName, List<Expression> argumentList){

		Object[] arguments = new Object[argumentList.size()];
		for(int i = 0; i < argumentList.size(); i ++) arguments[i] = argumentList.get(i);

		operands.add(expression);
		operands.add(methodName);
		operands.add(arguments);
	}

	public Object evaluate(Context context) {
		Expression expression = (Expression) operands.get(0);
		String methodName = (String) operands.get(1);
		Object[] expressions = (Object[]) operands.get(2);

		Object object = expression.evaluate(context);

		if (object == null)
		{
			return null;
		}

		Object[] arguments = new Object[expressions.length];
		Class<?>[] params = new Class[arguments.length];
		for (int i = 0; i < arguments.length; i ++) {
			if (expressions[i] == null) {
				arguments[i] = null;
				params[i] = null;
			} else {
				arguments[i] = ((Expression) expressions[i]).evaluate((context));
				params[i] = arguments[i] == null ? Object.class : arguments[i].getClass();
			}

			if (arguments[i] instanceof Integer) {
				params[i] = int.class;
			} else if (arguments[i] instanceof Float) {
				params[i] = float.class;
			}
		}

		Class<?> cls = object.getClass();
		Method method = findMethod(cls, methodName, params, 0);

		try {
			return method.invoke(object, arguments);
		} catch (Exception e) {
	    	System.err.println("Method \"" + methodName + "\" not found.");
	    	return null;
		}
	}

	private Method findMethod(Class<?> cls, String methodName, Class<?>[] params, int i) {
		if (i >= params.length) {
			return findMethod(cls, methodName, params);
		}
		Class<?> paramClass = params[i];
		while (paramClass != null) {
			params[i] = paramClass;
			Method method = findMethod(cls, methodName, params, i + 1);
			if (method != null) return method;
			paramClass = paramClass.getSuperclass();
		}
		return null;
	}

	private Method findMethod(Class<?> cls, String methodName, Class<?>[] params) {
		while (cls != null) {
			try {
				return cls.getMethod(methodName, params);
			} catch (NoSuchMethodException e) {
				cls = cls.getSuperclass();
			}
		}
		return null;
	}
}
