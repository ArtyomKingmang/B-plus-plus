package com.kingmang.bpp.ast;
import com.kingmang.bpp.Context;
import com.kingmang.bpp.math.Matrix;
import com.kingmang.bpp.math.Vector;


public abstract class CalcExpression extends Expression {
	protected Object op1;
	protected Object op2;
	protected Float op1f;
	protected Float op2f;
	protected Matrix op1m;
	protected Matrix op2m;
	protected Vector op1v;
	protected Vector op2v;
	public CalcExpression(Object op1, Object op2) {
		operands.add(op1);
		operands.add(op2);
	}
	public Object evaluate(Context context) {
		op1 = ((Expression) operands.get(0)).evaluate(context);
		op2 = ((Expression) operands.get(1)).evaluate(context);
		return null;
	}
	protected boolean prepareCalc() {
		op1f = op2f = null;
		op1m = op2m = null;
		op1v = op2v = null;
		if (op1 instanceof Float) {
			op1f = (Float) op1;
		} else if (op1 instanceof Matrix) {
			op1m = (Matrix) op1;
		} else if (op1 instanceof Vector) {
			op1v = (Vector) op1;
		}
		if (op2 instanceof Float) {
			op2f = (Float) op2;
		} else if (op2 instanceof Matrix) {
			op2m = (Matrix) op2;
		} else if (op2 instanceof Vector) {
			op2v = (Vector) op2;
		}
		return (op1f != null || op1m != null || op1v != null)
				|| (op2f != null || op2m != null || op2v != null);
	}
}
