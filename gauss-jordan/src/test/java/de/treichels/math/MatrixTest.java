package de.treichels.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

public class MatrixTest {
	@Test
	public void benchmark() {
		/** solve() ***************************************************************************/
		final double[][] data = { { 6, -4, -1, 5, 0 }, { 6, -6, -7, -3, 6 }, { -3, 9, 0, -7, -9 }, { 0, 7, 7, 0, -9 } };
		final Matrix matrix = new Matrix(data);

		long start = System.nanoTime();
		matrix.solve();
		long end = System.nanoTime();

		System.out.printf("solve() duration: %d\n", end - start);
		System.out.printf("{%1.10f; %1.10f; %1.10f; %1.10f}\n", matrix.get(0, 4), matrix.get(1, 4), matrix.get(2, 4), matrix.get(3, 4));

		/** LUDecomposition *******************************************************************/
		final RealMatrix coefficients = new Array2DRowRealMatrix(new double[][] { { 6, -4, -1, 5 }, { 6, -6, -7, -3 }, { -3, 9, 0, -7 }, { 0, 7, 7, 0 } },
				false);
		final RealVector constants = new ArrayRealVector(new double[] { 0, 6, -9, -9 });
		final DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

		start = System.nanoTime();
		final RealVector result2 = solver.solve(constants);
		end = System.nanoTime();

		System.out.printf("LU duration: %d\n", end - start);
		System.out.println(result2);

	}

	@Test
	public void testMatrixAdd() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix matrix = new Matrix(data);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		matrix.add(0, 1, 2);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		for (int column = 0; column < 5; column++) {
			assertEquals(column + 1, matrix.get(0, column), 1e-99d);
			assertEquals(6 + column + (1 + column) * 2, matrix.get(1, column), 1e-99d);
			assertEquals(11 + column, matrix.get(2, column), 1e-99d);
		}
	}

	@Test
	public void testMatrixArrayOfArrayOfDouble() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix matrix = new Matrix(data);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 5; column++) {
				assertEquals(row * 5 + column + 1, matrix.get(row, column), 1e-99d);
			}
		}
	}

	@Test
	public void testMatrixIntInt() {
		final Matrix matrix = new Matrix(3, 5);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 5; column++) {
				assertEquals(0, matrix.get(row, column), 1e-99d);
			}
		}
	}

	@Test
	public void testMatrixMatrix() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix other = new Matrix(data);
		final Matrix matrix = new Matrix(other);

		assertNotSame(other, matrix);
		assertEquals(other, matrix);

		assertEquals(3, other.getRows());
		assertEquals(5, other.getColums());

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 5; column++) {
				assertEquals(other.get(row, column), matrix.get(row, column), 1e-99d);
			}
		}
	}

	@Test
	public void testMatrixMultiply() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix matrix = new Matrix(data);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		matrix.multiply(1, 5);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		for (int column = 0; column < 5; column++) {
			assertEquals(column + 1, matrix.get(0, column), 1e-99d);
			assertEquals((6 + column) * 5, matrix.get(1, column), 1e-99d);
			assertEquals(11 + column, matrix.get(2, column), 1e-99d);
		}
	}

	@Test
	public void testMatrixSolve() {
		final double[][] data = { { 6, -4, -1, 5, 0 }, { 6, -6, -7, -3, 6 }, { -3, 9, 0, -7, -9 }, { 0, 7, 7, 0, -9 } };
		final Matrix matrix = new Matrix(data);

		assertEquals(4, matrix.getRows());
		assertEquals(5, matrix.getColums());

		matrix.solve();

		assertEquals(4, matrix.getRows());
		assertEquals(5, matrix.getColums());

		assertEquals(-26d / 49d, matrix.get(0, 4), 1e-6d);
		assertEquals(-81d / 49d, matrix.get(1, 4), 1e-6d);
		assertEquals(18d / 49d, matrix.get(2, 4), 1e-6d);
		assertEquals(-30d / 49d, matrix.get(3, 4), 1e-6d);
	}

	@Test
	public void testMatrixSwap() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix matrix = new Matrix(data);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		matrix.swap(1, 2);

		assertEquals(3, matrix.getRows());
		assertEquals(5, matrix.getColums());

		assertEquals(11, matrix.get(1, 0), 1e-99d);
		assertEquals(12, matrix.get(1, 1), 1e-99d);
		assertEquals(6, matrix.get(2, 0), 1e-99d);
		assertEquals(7, matrix.get(2, 1), 1e-99d);
	}

	@Test
	public void testMatrixToString() {
		final double[][] data = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		final Matrix matrix = new Matrix(data);

		assertEquals("|  1.00,   2.00,   3.00,   4.00,   5.00|\n|  6.00,   7.00,   8.00,   9.00,  10.00|\n| 11.00,  12.00,  13.00,  14.00,  15.00|\n",
				matrix.toString());
	}
}
