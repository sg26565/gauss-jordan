package de.treichels.math;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * A class representing a polynomal function (e.g. ax^3 + bx^2 + cx + d).
 *
 * @author Oliver Treichel &ltoli@treichels.de&gt;
 */
public class PolynomalFunction {
	private final double[] coefficients;
	private final int degree;

	/**
	 * Construct a polynomal function from coefficients.
	 *
	 * @param coefficients
	 */
	public PolynomalFunction(final double[] coefficients) {
		degree = coefficients.length - 1;
		this.coefficients = coefficients;
	}

	/**
	 * Construct a polynomal function with a given degree.
	 *
	 * @param degree
	 */
	public PolynomalFunction(final int degree) {
		this.degree = degree;
		coefficients = new double[degree + 1];
	}

	/**
	 * Construct a polynomal function as a copy of another polynomal function.
	 *
	 * @param other
	 */
	public PolynomalFunction(final PolynomalFunction other) {
		degree = other.degree;
		coefficients = Arrays.copyOf(other.coefficients, other.getCoefficients().length);
	}

	/**
	 * Two polynomal functions are equal, id their degree and coefficients
	 * match.
	 */
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
		return Arrays.equals(coefficients, other.coefficients);
	}

	/**
	 * Calculate the result of the polynomal function for a given x.
	 *
	 * @param x
	 * @return function value
	 */
	public double evaluate(final double x) {
		double result = 0;

		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, degree - i);
		}

		return result;
	}

	/**
	 * <p>
	 * Get the coefficients of this function.
	 * </p>
	 * <p>
	 * For the function <b>ax^3 + bx^2 + cx + d</b>
	 * </p>
	 *
	 * <pre>
	 * coefficients[0] == a; coefficients[1] == b; coefficients[2] == c; coefficients[3] == d;
	 * </pre>
	 *
	 * @return an array of the coefficents
	 */
	public double[] getCoefficients() {
		return coefficients;
	}

	/**
	 * The degree of the function.
	 * <p>
	 * E.g. the function <b>ax^3 + bx^2 + cx + d</b> has a degree of 3.
	 * </p>
	 *
	 * @return the degree
	 */
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

	/**
	 * Return a string representation of this function in a human readable form.
	 */
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		final NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(0);

		for (int i = 0; i < coefficients.length; i++) {
			double c = coefficients[i];

			if (c != 0) {
				if (b.length() > 0) {
					if (c < 0) {
						c = -c;
						b.append(" - ");
					} else {
						b.append(" + ");
					}
				}

				if (c != 1) {
					final String s = nf.format(c);
					b.append(s);
				}

				final int p = degree - i;
				switch (p) {
				case 0:
					// no x
					break;

				case 1:
					b.append(" x");
					break;

				default:
					b.append(" x^").append(p);
				}
			}
		}

		return b.toString();
	}
}
