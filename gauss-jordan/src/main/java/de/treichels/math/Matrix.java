package de.treichels.math;

import java.util.Arrays;

/**
 * <p>
 * A matrix representing a linear equation system.
 * </p>
 *
 * <b>Example:</b> Given the three equations where all a<sub>i</sub>,
 * b<sub>i</sub>, c<sub>i</sub> and d<sub>i</sub> are known ...
 *
 * <pre>
 * a<sub>1</sub> x<sub>1</sub> + b<sub>1</sub> x<sub>2</sub> + c<sub>1</sub> x<sub>3</sub> = d<sub>1</sub>
 * a<sub>2</sub> x<sub>1</sub> + b<sub>2</sub> x<sub>2</sub> + c<sub>2</sub> x<sub>3</sub> = d<sub>2</sub>
 * a<sub>3</sub> x<sub>1</sub> + b<sub>3</sub> x<sub>2</sub> + c<sub>3</sub> x<sub>3</sub> = d<sub>3</sub>
 * </pre>
 *
 * ... could be written as a matrix:
 *
 * <pre>
 * |a<sub>1</sub> b<sub>1</sub> c<sub>1</sub> d<sub>1</sub>|
 * |a<sub>2</sub> b<sub>2</sub> c<sub>2</sub> d<sub>2</sub>|
 * |a<sub>3</sub> b<sub>3</sub> c<sub>3</sub> d<sub>3</sub>|
 * </pre>
 *
 * Using the Gauss-Jordan algorithm, the matrix can be transformed, showing the
 * solutions in the last column:
 *
 * <pre>
 * |1 0 0 x<sub>1</sub>|
 * |0 1 0 x<sub>2</sub>|
 * |0 0 1 x<sub>3</sub>|
 * </pre>
 *
 * @author Oliver Treichel &lt;oli@treichels.de&gt;
 *
 */
public class Matrix {
	private final double[][] data;

	/**
	 * Construct a matrix from existing data.
	 *
	 * @param data
	 */
	public Matrix(final double[][] data) {
		this.data = data;
	}

	/**
	 * Construct an empty matrix with given dimensions
	 *
	 * @param rows
	 * @param columns
	 */
	public Matrix(final int rows, final int columns) {
		data = new double[rows][columns];

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				data[row][column] = 0;
			}
		}
	}

	/**
	 * Construct a matrix as a copy of another matrix.
	 *
	 * @param other
	 */
	public Matrix(final Matrix other) {
		final int rows = other.getRows();
		final int columns = other.getColums();
		data = new double[rows][columns];

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				data[row][column] = other.data[row][column];
			}
		}
	}

	/**
	 * Add row1 to row2 and store the result in row2.
	 *
	 * @param row1
	 * @param row2
	 */
	public void add(final int row1, final int row2) {
		add(row1, row2, 1);
	}

	/**
	 * Add row1 multiplied with a factor to row2 and store the result in row2.
	 *
	 * @param row1
	 * @param row2
	 */
	public void add(final int row1, final int row2, final double factor) {
		final int colums = getColums();

		for (int column = 0; column < colums; column++) {
			data[row2][column] += data[row1][column] * factor;
		}
	}

	/**
	 * Two matrixes are equal if their data is equal.
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
		return Arrays.deepEquals(data, ((Matrix) obj).data);
	}

	/**
	 * Get a cell value.
	 *
	 * @param row
	 * @param column
	 * @return the cell value
	 */
	public double get(final int row, final int column) {
		return data[row][column];
	}

	/**
	 * Get all values in a column.
	 *
	 * @param column
	 * @return column data
	 */
	public double[] getColumn(final int column) {
		final int rows = getRows();
		final double[] result = new double[rows];

		for (int row = 0; row < rows; row++) {
			result[row] = data[row][column];
		}

		return result;
	}

	/**
	 * Get the number of columns.
	 *
	 * @return the column dimension
	 */
	public int getColums() {
		return data[0].length;
	}

	/**
	 * Get the data for this matrix as an array of array of double.
	 *
	 * @return the data
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * Get all values in a row.
	 *
	 * @param row
	 * @return the row data
	 */
	public double[] getRow(final int row) {
		final int columns = getColums();
		final double[] result = new double[columns];

		for (int column = 0; column < columns; column++) {
			result[column] = data[row][column];
		}

		return result;
	}

	/**
	 * Get the number of rows.
	 *
	 * @return the row dimension
	 */
	public int getRows() {
		return data.length;
	}

	/**
	 * The hash code is calculated from the data array.
	 */
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(data);
	}

	/**
	 * Multiply a row with a factor.
	 *
	 * @param row
	 * @param factor
	 */
	public void multiply(final int row, final double factor) {
		final int colums = getColums();

		for (int column = 0; column < colums; column++) {
			data[row][column] *= factor;
		}
	}

	/**
	 * <p>
	 * Solve the matrix using the Gauss-Jordan algorithm.
	 * </p>
	 *
	 * For each row in the matrix, perform the following steps:
	 * <ol>
	 * <li>
	 * <p>
	 * Make sure the cell value at the main diagonal (with row == column) is not
	 * 0 by swapping this row with one below.
	 * </p>
	 * <b>Example:</b> Swap rows 0 and 1
	 *
	 * <pre>
	 * |0 1 2 3|    |5 7 2 7|
	 * |5 7 2 7| => |0 1 2 3|
	 * |8 4 1 1|    |8 4 1 1|
	 * </pre>
	 *
	 * </li>
	 * <li>
	 * <p>
	 * Normalize the call value (i.e. set it to 1) by dividing the entire row by
	 * the current cell value.
	 * </p>
	 * <b>Example:</b> divide row 1 by 5
	 *
	 * <pre>
	 * |5 7 2 7|    |1 7/5 2/5 7/5|
	 * |0 1 2 3| => |0  1   2   3 |
	 * |8 4 0 1|    |8  4   0   1 |
	 * </pre>
	 *
	 * </li></li>
	 * <li>
	 * <p>
	 * Set the cell values of all other rows to 0 for the current column by
	 * subtracting a multiple of this row from the other row
	 * </p>
	 * <b>Example:</b> Subtract 8 times row 0 from row 3
	 *
	 * <pre>
	 * |1 7/5 2/5 7/5|    |1   7/5   2/5   7/5 |
	 * |0  1   2   3 | => |0    1     2     3  |
	 * |8  4   0   1 |    |0  -36/5 -16/5 -51/5|
	 * </pre>
	 *
	 * </li>
	 * </ol>
	 */
	public void solve() {
		final int rows = getRows();
		final int columns = getColums();

		if (columns != rows + 1) {
			throw new IllegalArgumentException("malformed matrix, wrong dimensions!");
		}

		for (int row = 0; row < rows; row++) {
			// make sure cell (row, row) is not zero
			if (data[row][row] == 0) {
				int swapRow = row + 1;
				while (true) {
					if (swapRow >= rows) {
						throw new IllegalArgumentException("unsolvable matrix!");
					}

					if (data[swapRow][row] != 0) {
						swap(row, swapRow);
						break;
					} else {
						swapRow++;
					}
				}
			}

			// normalize
			multiply(row, 1d / data[row][row]);

			// substract this row from all other rows
			for (int subsRow = 0; subsRow < rows; subsRow++) {
				if (subsRow != row && data[subsRow][row] != 0) {
					add(row, subsRow, -data[subsRow][row]);
				}
			}
		}
	}

	/**
	 * Swap two columns (row1 = row2 and row2 = row1).
	 *
	 * @param row1
	 * @param row2
	 */
	public void swap(final int row1, final int row2) {
		final int colums = getColums();

		for (int column = 0; column < colums; column++) {
			final double temp = data[row1][column];
			data[row1][column] = data[row2][column];
			data[row2][column] = temp;
		}
	}

	@Override
	public String toString() {
		final int rows = getRows();
		final int columns = getColums();

		final StringBuilder builder = new StringBuilder();

		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if (column == 0) {
					builder.append('|');
				}

				builder.append(String.format("%6.2f", data[row][column]));

				if (column == columns - 1) {
					builder.append("|\n");
				} else {
					builder.append(", ");
				}
			}
		}

		return builder.toString();
	}
}