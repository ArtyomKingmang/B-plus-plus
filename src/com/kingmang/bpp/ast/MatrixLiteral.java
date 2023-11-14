package com.kingmang.bpp.ast;
import java.util.ArrayList;
import java.util.List;

import com.kingmang.bpp.Context;
import com.kingmang.bpp.math.Matrix2x2f;
import com.kingmang.bpp.math.Vector2f;

public class MatrixLiteral extends Expression {
	public MatrixLiteral(List<Expression> elementList) {
		operands.add(elementList);
	}

	@SuppressWarnings("unchecked")
	public Object evaluate(Context context) {
		List<Expression> elementList = (List<Expression>) operands.get(0);
		List<Float> valueList = new ArrayList<Float>(4);
		for (Expression element : elementList) {
			Object ret = element.evaluate(context);
			if (ret == null) {
				valueList.add(Float.NaN);
			} else if (ret instanceof Float) {
				valueList.add((Float) ret);
			} else try {
				valueList.add(Float.valueOf(ret.toString()));
			} catch (NumberFormatException nfe) {
				valueList.add(Float.NaN);
			} 
		}
		if (valueList.size() == 4) {
			Matrix2x2f m = new Matrix2x2f();
			for (int i = 0; i < 4; i ++) {
				m.set(i % 2, i / 2, valueList.get(i));
			}
			return m;
		}
		if (valueList.size() == 2) {
			return new Vector2f(
					valueList.get(0), valueList.get(1));
		}
		return null;
	}
}