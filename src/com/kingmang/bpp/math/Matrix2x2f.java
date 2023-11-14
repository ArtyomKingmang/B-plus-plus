package com.kingmang.bpp.math;

public class Matrix2x2f extends Matrix {

	public static final Matrix2x2f ZERO = new Matrix2x2f(0, 0, 0, 0);
	public static final Matrix2x2f IDENTITY = new Matrix2x2f(1, 0, 0, 1);

	private static Matrix2x2f tmp;
	private static Vector2f vtmp;

	public Matrix2x2f() {
		super(4);
	}

	public Matrix2x2f(float m00, float m10, float m01, float m11) {
		this();
		data[0] = m00;
		data[1] = m10;
		data[2] = m01;
		data[3] = m11;
	}

	public Matrix2x2f(float[] data) {
		this();
		set(data);
	}

	public Matrix2x2f(Matrix orig) {
		this();
		set(orig);
	}

	@Override
	public int getColumns() {
		return 2;
	}

	@Override
	public int getRows() {
		return 2;
	}

	@Override
	public Vector getColumn(int i) {
		return new Vector2f(data[i + 0*getColumns()], data[i + 1
				* getColumns()]);
	}

	@Override
	public Vector getRow(int j) {
		return new Vector2f(data[0 + j*getColumns()], data[1 + j
				* getColumns()]);
	}

	@Override
	public float determinant() {
		return data[0 + 0*2]*data[1 + 1*2] - data[0 + 1*2]
				* data[1 + 0*2];
	}

	@Override
	public void inverseOut(Matrix result) {
		tmp().data[0 + 0*2] = data[1 + 1*2];
		tmp().data[1 + 0*2] = -data[1 + 0*2];
		tmp().data[0 + 1*2] = -data[0 + 1*2];
		tmp().data[1 + 1*2] = data[0 + 0*2];
		result.set(tmp());
	}

	public static float invSqrt(float x) {
		float xhalf = 0.5f * x;

		int i = Float.floatToIntBits(x);

		i = 0x5f3759d5 - (i >> 1);

		x = Float.intBitsToFloat(i);

		x = x * (1.5f - xhalf * x * x);
		return x;
	}

	@Override
	protected Matrix tmp() {
		if (tmp == null) {
			tmp = new Matrix2x2f();
		}
		return tmp;
	}

	@Override
	protected Vector vtmp() {
		if (vtmp == null) {
			vtmp = new Vector2f();
		}
		return vtmp;
	}

}
