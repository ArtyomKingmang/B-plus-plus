package com.kingmang.bpp.math;

import java.util.Arrays;

public abstract class Matrix {
	protected float[] data;

	public Matrix(int size) {
		data = new float[size];
	}

	public abstract int getColumns();

	public abstract int getRows();

	public Matrix set(Matrix orig) {
		set(orig.data);
		return this;
	}

	public Matrix set(float[] data) {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = data[i];
		}
		return this;
	}

	public Matrix set(int i, int j, float value) {
		data[i + j*getColumns()] = value;
		return this;
	}

	public Matrix setRow(Vector row, int r) {
		assert(row.size() == getColumns());
		for (int i = 0; i < getColumns(); i++) {
			this.data[i + r*getColumns()] = row.data[i];
		}
		return this;
	}

	public Matrix setColumn(Vector column, int c) {
		assert(column.size() == getRows());
		for (int i = 0; i < getColumns(); i++) {
			this.data[c + i*getColumns()] = column.data[i];
		}
		return this;
	}

	public float get(int i, int j) {
		return data[i + j*getColumns()];
	}

	public abstract Vector getColumn(int i);

	public abstract Vector getRow(int j);

	public abstract float determinant();

	public Matrix sum(Matrix vec) {
		Matrix result = Matrix.newInstance(this);
		sumOut(vec, result);
		return result;
	}

	public Matrix sub(Matrix vec) {
		Matrix result = Matrix.newInstance(this);
		subOut(vec, result);
		return result;
	}

	public Matrix mul(float scalar) {
		Matrix result = Matrix.newInstance(this);
		mulOut(scalar, result);
		return result;
	}

	public Matrix mul(Matrix right) {
		Matrix result = Matrix.newInstance(this);
		mulOut(right, result);
		return result;
	}

	public Vector mul(Vector right) {
		assert(right.size() == getRows());
		Vector result = Vector.newInstance(right.size());
		mulOut(right, result);
		return result;
	}

	public Matrix transpose() {
		Matrix result = Matrix.newInstance(this);
		transposeOut(result);
		return result;
	}

	public Matrix inverse() {
		Matrix result = Matrix.newInstance(this);
		inverseOut(result);
		return result;
	}

	public void sumOut(Matrix matrix, Matrix result) {
		assert(getColumns() == matrix.getColumns() && getRows() == matrix.getRows());
		assert(getColumns() == result.getColumns() && getRows() == result.getRows());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] + matrix.data[i];
		}
	}

	public void subOut(Matrix matrix, Matrix result) {
		assert(getColumns() == matrix.getColumns() && getRows() == matrix.getRows());
		assert(getColumns() == result.getColumns() && getRows() == result.getRows());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] - matrix.data[i];
		}
	}

	public void mulOut(float scalar, Matrix result) {
		assert(getColumns() == result.getColumns() && getRows() == result.getRows());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] * scalar;
		}
	}

	public void mulOut(Matrix matrix, Matrix result) {
		assert(getRows() == matrix.getColumns()
				&& getColumns() == result.getColumns()
				&& matrix.getRows() == result.getRows());
		for (int i = 0; i < getColumns(); i++) {
			for (int j = 0; j < matrix.getRows(); j++) {
				float sum = 0f;
				for (int k = 0; k < getRows(); k++) {
					float cell = data[i + k*getColumns()];
					cell *= matrix.data[k + j*matrix.getColumns()];
					sum += cell;
				}
				tmp().data[i + j*getColumns()] = sum;
			}
		}
		result.set(tmp());
	}

	public void mulOut(Vector vector, Vector result) {
		assert(vector.size() == result.size() && vector.size() == getRows());
		for (int i = 0; i < getColumns(); i++) {
			float sum = 0f;
			for (int j = 0; j < getRows(); j++) {
				sum += data[i + j*getColumns()] * vector.data[j];
			}
			vtmp().data[i] = sum;
		}
		result.set(vtmp());
	}

	public void transposeOut(Matrix result) {
		assert(getColumns() == getRows());
		assert(getColumns() == result.getColumns() && getRows() == result.getRows());
		for (int i = 0; i < getColumns(); ++ i) {
			for (int j = 0; j < getRows(); ++ j) {
				tmp().data[i + j*getColumns()] = data[j + i*getColumns()];
			}
		}
		result.set(tmp());
	}

	public abstract void inverseOut(Matrix result);

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getColumns(); i ++) {
			for (int j = 0; j < getRows(); j ++) {
				float v = get(i, j);
				if (v >= 0) sb.append(" ");
				sb.append(String.format("%.5f", v));
				if (j < getRows() - 1) sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Matrix) {
			return Arrays.equals(data, ((Matrix)o).data);
		}
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	public static Matrix newInstance(Matrix matrix) {
		if (matrix instanceof Matrix2x2f) {
			return new Matrix2x2f(matrix);
		}
		if (matrix instanceof Matrix3x3f) {
			return new Matrix3x3f(matrix);
		}
		return null;
	}

	public static Matrix newInstance(int columns, int rows) {
		if (columns == 2 && rows == 2) {
			return new Matrix2x2f();
		}
		if (columns == 3 && rows == 3) {
			return new Matrix3x3f();
		}
		return null;
	}

	protected abstract Matrix tmp();

	protected abstract Vector vtmp();

}
