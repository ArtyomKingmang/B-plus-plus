package com.kingmang.bpp.math;

public class Matrix3x3f extends Matrix {

	public static final Matrix3x3f ZERO =
			new Matrix3x3f(0, 0, 0, 0, 0, 0, 0, 0, 0);
	public static final Matrix3x3f IDENTITY =
			new Matrix3x3f(1, 0, 0, 0, 1, 0, 0, 0, 1);

	private static Matrix3x3f tmp;
	private static Vector3f vtmp;

	public Matrix3x3f() {
		super(9);
	}

	public Matrix3x3f(
			float m00, float m10, float m20,
			float m01, float m11, float m21,
			float m02, float m12, float m22) {
		this();
		data[0] = m00;
		data[1] = m10;
		data[2] = m20;
		data[3] = m01;
		data[4] = m11;
		data[5] = m21;
		data[6] = m02;
		data[7] = m12;
		data[8] = m22;
	}

	public Matrix3x3f(float[] data) {
		this();
		set(data);
	}

	public Matrix3x3f(Matrix orig) {
		this();
		set(orig);
	}

	@Override
	public int getColumns() {
		return 3;
	}

	@Override
	public int getRows() {
		return 3;
	}

	@Override
	public Vector getColumn(int i) {
		return new Vector3f(
				data[i + 0*getColumns()],
				data[i + 1*getColumns()],
				data[i + 2*getColumns()]);
	}

	@Override
	public Vector getRow(int j)  {
		return new Vector3f(
				data[0 + j*getColumns()],
				data[1 + j*getColumns()],
				data[2 + j*getColumns()]);
	}

	@Override
	public float determinant() {
		return data[0 + 0*3]*(data[1 + 1*3]*data[2 + 2*3] - data[2 + 1*3]*data[1 + 2*3])
			 - data[0 + 1*3]*(data[1 + 0*3]*data[2 + 2*3] - data[2 + 0*3]*data[1 + 2*3])
			 + data[0 + 2*3]*(data[1 + 0*3]*data[2 + 1*3] - data[2 + 0*3]*data[1 + 1*3]);
	}

	@Override
	public void inverseOut(Matrix result) {
		tmp().data[0 + 0*3] = data[1 + 1*3]*data[2 + 2*3] - data[1 + 2*3]*data[2 + 1*3];
		tmp().data[0 + 1*3] = data[0 + 2*3]*data[2 + 1*3] - data[0 + 1*3]*data[2 + 2*3];
		tmp().data[0 + 2*3] = data[0 + 1*3]*data[1 + 2*3] - data[0 + 2*3]*data[1 + 1*3];
		tmp().data[1 + 0*3] = data[1 + 2*3]*data[2 + 0*3] - data[1 + 0*3]*data[2 + 2*3];
		tmp().data[1 + 1*3] = data[0 + 0*3]*data[2 + 2*3] - data[0 + 2*3]*data[2 + 0*3];
		tmp().data[1 + 2*3] = data[0 + 2*3]*data[1 + 0*3] - data[0 + 0*3]*data[1 + 2*3];
		tmp().data[2 + 0*3] = data[1 + 0*3]*data[2 + 1*3] - data[1 + 1*3]*data[2 + 0*3];
		tmp().data[2 + 1*3] = data[0 + 1*3]*data[2 + 0*3] - data[0 + 0*3]*data[2 + 1*3];
		tmp().data[2 + 2*3] = data[0 + 0*3]*data[1 + 1*3] - data[0 + 1*3]*data[1 + 0*3];
		result.set(tmp());
	}

	@Override
	protected Matrix tmp() {
		if (tmp == null) {
			tmp = new Matrix3x3f();
		}
		return tmp;
	}

	@Override
	protected Vector vtmp() {
		if (vtmp == null) {
			vtmp = new Vector3f();
		}
		return vtmp;
	}

}
