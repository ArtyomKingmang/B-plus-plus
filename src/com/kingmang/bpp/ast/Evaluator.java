package com.kingmang.bpp.ast;
import java.util.*;

import com.kingmang.bpp.Context;

public abstract class Evaluator {
	private final String DEFAULT_CLASSNAME_PREFIX =
		"class com.kingmang.bpp.ast.";
	protected List<Object> operands = new ArrayList<Object>();
	protected Evaluator() {};

	public abstract Object evaluate(Context context);
	public Object getOperand(int index) { return operands.get(index); }
	public String toString() { return getClassName()+operands.toString(); }
	private String getClassName() {
		String className = this.getClass().toString();
		if (className.startsWith(
				DEFAULT_CLASSNAME_PREFIX))
		{
			return className.substring(
					DEFAULT_CLASSNAME_PREFIX.length());
		}
		else
		{
			return className;
		}
	}
}
