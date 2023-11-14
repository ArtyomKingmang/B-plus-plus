package com.kingmang.bpp.math;

public class Vector3f extends Vector {
	public static final Vector ZERO = new Vector3f();
	public static final Vector UNIT_X = new Vector3f(1, 0, 0);
	public static final Vector UNIT_Y = new Vector3f(0, 1, 0);
	public static final Vector UNIT_Z = new Vector3f(0, 0, 1);

	public Vector3f() {
		super(3);
	}

	public Vector3f(Vector orig) {
		this();
		set(orig);
	}

	public Vector3f(float[] data) {
		this();
		set(data);
	}

	public Vector3f(float scalar) {
		this();
		set(scalar);
	}

	public Vector3f(float x, float y, float z) {
		this();
		set(x, y, z);
	}

	@Override
	public void crossProductOut(Vector vec, Vector result) {
		result.data[0] = data[1] * vec.data[2] - data[2] * vec.data[1];
		result.data[1] = data[2] * vec.data[0] - data[0] * vec.data[2];
		result.data[2] = data[0] * vec.data[1] - data[1] * vec.data[0];
	}

}