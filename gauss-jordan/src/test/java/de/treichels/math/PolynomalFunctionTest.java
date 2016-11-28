package de.treichels.math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class PolynomalFunctionTest {

	@Test
	public void testEqualsObject() {
		final double[] coefficients1 = new double[] { 1, 2, 3 };
		final double[] coefficients2 = new double[] { 2, 1, 3 };

		final PolynomalFunction p1 = new PolynomalFunction(coefficients1);
		final PolynomalFunction p2 = new PolynomalFunction(coefficients1);
		final PolynomalFunction p3 = new PolynomalFunction(coefficients2);

		assertEquals(p1, p2);
		assertNotEquals(p1, p3);
	}

	@Test
	public void testEvaluate() {
		final double[] coefficients = new double[] { 1, 2, 3 };
		final PolynomalFunction p = new PolynomalFunction(coefficients);

		assertEquals(3, p.evaluate(0), 1e-99d);
	}

	@Test
	public void testGetCoefficients() {
		final double[] coefficients = new double[] { 1, 2, 3 };
		final PolynomalFunction p = new PolynomalFunction(coefficients);

		assertNotNull(p);
		assertEquals(2, p.getDegree());
		assertSame(coefficients, p.getCoefficients());
	}

	@Test
	public void testGetDegree() {
		final double[] coefficients = new double[] { 1, 2, 3 };
		final PolynomalFunction p = new PolynomalFunction(coefficients);

		assertNotNull(p);
		assertEquals(2, p.getDegree());
	}

	@Test
	public void testHashCode() {
		final double[] coefficients1 = new double[] { 1, 2, 3 };
		final double[] coefficients2 = new double[] { 2, 1, 3 };

		final PolynomalFunction p1 = new PolynomalFunction(coefficients1);
		final PolynomalFunction p2 = new PolynomalFunction(coefficients1);
		final PolynomalFunction p3 = new PolynomalFunction(coefficients2);

		assertEquals(p1.hashCode(), p2.hashCode());
		assertNotEquals(p1.hashCode(), p3.hashCode());
	}

	@Test
	public void testPolynomalFunctionDoubleArray() {
		final double[] coefficients = new double[] { 1, 2, 3 };
		final PolynomalFunction p = new PolynomalFunction(coefficients);

		assertNotNull(p);
		assertEquals(2, p.getDegree());
		assertSame(coefficients, p.getCoefficients());
	}

	@Test
	public void testPolynomalFunctionInt() {
		final int degree = 3;
		final PolynomalFunction p = new PolynomalFunction(degree);
		final double[] coefficients = p.getCoefficients();

		assertNotNull(p);
		assertEquals(degree, p.getDegree());

		assertNotNull(coefficients);
		assertEquals(degree + 1, coefficients.length);
		assertArrayEquals(new double[] { 0, 0, 0, 0 }, coefficients, 1e-99d);
	}

	@Test
	public void testPolynomalFunctionPolynomalFunction() {
		final double[] otherCoefficients = new double[] { 1, 2, 3 };
		final PolynomalFunction other = new PolynomalFunction(otherCoefficients);

		final PolynomalFunction p = new PolynomalFunction(other);
		final double[] coefficients = p.getCoefficients();

		assertNotNull(p);
		assertEquals(other.getDegree(), p.getDegree());
		assertNotNull(coefficients);
		assertEquals(otherCoefficients.length, coefficients.length);
		assertNotSame(otherCoefficients, coefficients);
		assertArrayEquals(otherCoefficients, coefficients, 1e-99d);
	}

	@Test
	public void testPolynomalFunctionToString() {
		final double[] coefficients = new double[] { 0, .8, 0, -3.4, 4 };
		final PolynomalFunction p = new PolynomalFunction(coefficients);

		assertEquals("0.8 x^3 - 3.4 x + 4", p.toString());
	}
}
