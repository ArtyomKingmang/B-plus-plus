package com.kingmang.bpp.math;

import java.util.Arrays;

public abstract class Vector {

	public float[] data;

	public Vector(int size) {
		data = new float[size];
	}

	public void x(float x) {
		data[0] = x;
	}

	public void y(float y) {
		data[1] = y;
	}

	public void z(float z) {
		data[2] = z;
	}

	public Vector set(Vector vec) {
		set(vec.data);
		return this;
	}

	public Vector set(float... data) {
		if (data.length != size()) {
			this.data = new float[data.length];
		}
		for (int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
		return this;
	}

	public Vector set(float scalar) {
		for (int i = 0; i < data.length; i++) {
			data[i] = scalar;
		}
		return this;
	}

	public void set(float val, int i) {
		data[i] = val;
	}

	public float x() {
		return data[0];
	}

	public float y() {
		return data[1];
	}

	public float z() {
		return data[2];
	}

	public float get(int i) {
		return data[i];
	}

	public boolean eq(Vector vec) {
		if (vec.size() != size())
			return false;
		for (int i = 0; i < size(); i++) {
			if (data[i] != vec.data[i])
				return false;
		}
		return true;
	}

	public float length() {
		return (float) Math.sqrt(squaredLength());
	}

	public float squaredLength() {
		float squaredLength = 0;
		for (int i = 0; i < size(); i++)
			squaredLength += data[i] * data[i];
		return squaredLength;
	}

	public float normalize() {
		float length = length();
		if (length > 0) {
			float invLength = 1.0f / length;
			for (int i = 0; i < size(); i++)
				data[i] *= invLength;
		}
		return length;
	}

	public Vector sum(Vector vec) {
		Vector result = Vector.newInstance(size());
		sumOut(vec, result);
		return result;
	}

	public Vector sub(Vector vec) {
		Vector result = Vector.newInstance(size());
		subOut(vec, result);
		return result;
	}

	public Vector mul(float scalar) {
		Vector result = Vector.newInstance(size());
		mulOut(scalar, result);
		return result;
	}

	public Vector mul(Vector vec) {
		Vector result = Vector.newInstance(size());
		mulOut(vec, result);
		return result;
	}

	public Matrix mulWithTranspose(Vector vector) {
		Matrix result = Matrix.newInstance(size(), size());
		mulWithTransposeOut(vector, result);
		return result;
	}

	public Vector div(float fScalar) {
		Vector result = Vector.newInstance(size());
		divOut(fScalar, result);
		return result;
	}

	public Vector div(Vector vec) {
		Vector result = Vector.newInstance(size());
		divOut(vec, this);
		return result;
	}

	public float dotProduct(Vector vec) {
		assert(vec.size() == size());
		float sum = 0;
		for (int i = 0; i < size(); i++) {
			sum += data[i] * vec.data[i];
		}
		return sum;
	}

	public Vector crossProduct(Vector vec) {
		Vector result = Vector.newInstance(size());
		crossProductOut(vec, result);
		return result;
	}

	public void sumOut(Vector vec, Vector result) {
		assert(size() == vec.size() && size() == result.size());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] + vec.data[i];
		}
	}

	public void subOut(Vector vec, Vector result) {
		assert(size() == vec.size() && size() == result.size());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] - vec.data[i];
		}
	}

	public void mulOut(float scalar, Vector result) {
		assert(size() == result.size());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] * scalar;
		}
	}

	public void mulOut(Vector vec, Vector result) {
		assert(size() == vec.size() && size() == result.size());
		for (int i = 0; i < data.length; i++) {
			result.data[i] = data[i] * vec.data[i];
		}
	}

	public void mulWithTransposeOut(Vector vec, Matrix result) {
		assert(size() == vec.size()
				&& size() == result.getColumns()
				&& size() == result.getRows());
		for (int i = 0; i < size(); ++i) {
			for (int j = 0; j < size(); ++j) {
				result.data[i + j * size()] = data[i] * vec.data[j];
			}
		}
	}

	public void divOut(float scalar, Vector result) {
		float inv = 1.0f / scalar;
		mulOut(inv, result);
	}

	public void divOut(Vector vec, Vector result) {
		assert(size() == vec.size() && size() == result.size());
		for (int i = 0; i < size(); i++) {
			result.data[i] = data[i] / vec.data[i];
		}
	}

	public abstract void crossProductOut(Vector vec, Vector result);

	public int size() {
		return data.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < data.length; i ++)
			sb.append(String.format("%.5f, ", data[i]));
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			return Arrays.equals(data, ((Vector)o).data);
		}
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	public static Vector newInstance(int size) {
		switch (size) {
		case 2:
			return new Vector2f();
		case 3:
			return new Vector3f();
		}
		return null;
	}

}
