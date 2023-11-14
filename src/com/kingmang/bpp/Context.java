package com.kingmang.bpp;
import java.util.*;

import com.kingmang.bpp.ast.Evaluator;


public class Context {
	private Context parentContext;
	HashSet<Object> objects;
	public Hashtable<String, Variable> variables;
	public Hashtable<String, Function> functions;
	public List<Evaluator> code;

	public Context()
	{
		variables = new Hashtable<String, Variable>();
		functions = new Hashtable<String, Function>();
		objects = new HashSet<Object>();
		code = new ArrayList<Evaluator>();
	}

	public void print() {
		System.out.println("*variables");
		for (Object key : variables.keySet())
			System.out.println("  "+key+" = "+ ((Variable)variables.get(key)).value);
		System.out.println("*functions");
		for (Object key : functions.keySet())
			System.out.println("  "+key+" : "+ functions.get(key));
		System.out.println("*code");
		for (int i = 0; i < code.size(); i ++)
			System.out.println("  "+i+": "+code.get(i));
		System.out.println("*objects");
		for (Object object : objects)
			System.out.println(" "+object);
		System.out.println();
	}

	public Object run() {
		Object result = null;
		for (int i = 0; i < code.size(); i++) {
			final Evaluator instruction = code.get(i);
			result = instruction.evaluate(this);
		}
		return result;
	}

	public void registerObject(Object object)
	{
		objects.add(object);
	}

	/** Returns the raw set of objects instantiated in the context. */
	public HashSet<Object> getObjects()
	{
		final Context rootContext = getRootContext();
		if (rootContext.objects == null)
		{
			rootContext.objects = new HashSet<Object>();
		}
		return rootContext.objects;
	}

	private Context getRootContext()
	{
		Context rootContext = this;
		while (rootContext.parentContext != null)
		{
			rootContext = rootContext.parentContext;
		}
		return rootContext;
	}

	public void merge(Context c) {
		variables.putAll(c.variables);
		functions.putAll(c.functions);
		code.addAll(c.code);
	}

	public Context createLocalContext() {
		Context context = new Context();
		context.parentContext = this;
		return context;
	}

	public Context createLocalContext(List<Evaluator> code) {
		final Context context = createLocalContext();
		context.code = code;
		return context;
	}

	public void addVariable(Variable variable) { variables.put(variable.name, variable); }

	public Variable createVariable(String variableName) {
		final Variable variable = new Variable(variableName, null);
		addVariable(variable);
		return variable;
	}

	public Variable getVariable(String variableName) {
		Context context = this;

		while (true) {
			Variable variable = context.variables.get(variableName);
			if (variable != null) return variable;
			if (context.parentContext != null) context = context.parentContext;
			else break;
		}

		return null;
	}

	public void addFunction(Function function) { functions.put(function.name, function); }

	public Function getFunction(String functionName) {
		Context context = this;

		while (true) {
			Function function = context.functions.get(functionName);
			if (function != null) return function;
			if (context.parentContext != null) context = context.parentContext;
			else break;
		}

		return null;
	}

}
