package com.kingmang.bpp.math;

public class Vector2f extends Vector {
	public static final Vector ZERO = new Vector2f();
	public static final Vector UNIT_X = new Vector2f(1, 0);
	public static final Vector UNIT_Y = new Vector2f(0, 1);

	public Vector2f() {
		super(2);
	}

	public Vector2f(Vector orig) {
		this();
		set(orig);
	}

	public Vector2f(float[] data) {
		this();
		set(data);
	}

	public Vector2f(float scalar) {
		this();
		set(scalar);
	}

	public Vector2f(float x, float y) {
		this();
		set(x, y);
	}

	@Override
	public Vector crossProduct(Vector vec) {
		Vector result = Vector.newInstance(3);
		crossProductOut(vec, result);
		return result;
	}

	@Override
	public void crossProductOut(Vector vec, Vector result) {
		result.data[0] = 0;
		result.data[1] = 0;
		result.data[2] = data[0] * vec.data[1] - data[1] * vec.data[0];
	}

}
