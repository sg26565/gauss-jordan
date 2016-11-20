package de.treichels.math;

import java.util.Arrays;

/**
 * A class representing a polynomal function (e.g. ax^3 + bx^2 + cx + d).
 *
 * @author Oliver Treichel &ltoli@treichels.de&gt;
 */
public class PolynomalFunction {
	private final double[] coefficients;
	private final int degree;

	public PolynomalFunction(final double[] coefficients) {
		degree = coefficients.length - 1;
		this.coefficients = coefficients;
	}

	public PolynomalFunction(final int degree) {
		this.degree = degree;
		coefficients = new double[degree + 1];
	}

	public PolynomalFunction(final PolynomalFunction other) {
		degree = other.degree;
		coefficients = Arrays.copyOf(other.coefficients, other.getCoefficients().length);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PolynomalFunction other = (PolynomalFunction) obj;
		if (degree != other.degree) {
			return false;
		}
		if (!Arrays.equals(coefficients, other.coefficients)) {
			return false;
		}
		return true;
	}

	public double evaluate(final double x) {
		double result = 0;

		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, degree - i);
		}

		return result;
	}

	public double[] getCoefficients() {
		return coefficients;
	}

	public int getDegree() {
		return degree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coefficients);
		result = prime * result + degree;
		return result;
	}
}
