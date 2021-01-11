package math

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Matrix(
    val rows: Int,
    val columns: Int,
    override val elements: DoubleArray
) : ElementOperations<Matrix> {
//    override val shape: Shape get() = Shape.D2(rows, columns)
    constructor(rows: Int, columns: Int) : this(rows, columns, DoubleArray(rows * columns))

    override fun copy(elements: DoubleArray): Matrix {
        return Matrix(rows, columns, elements)
    }

    operator fun get(row: Int, column: Int): Double {
        return elements[row * columns + column]
    }

    operator fun set(row: Int, column: Int, value: Double) {
        elements[row * columns + column] = value
    }

    operator fun times(other: Matrix): Matrix {
        require(this.columns == other.rows) {
            "cannot multiply matrix of size ($rows, $columns) with matrix of size (${other.rows}, ${other.columns})"
        }

        val newMatrix = Matrix(this.rows, other.columns)

        for (y in 0 until newMatrix.rows) {
            for (x in 0 until newMatrix.columns) {
                var sum = 0.0
                for (i in 0 until other.rows) {
                    sum += this[y, i] * other[i, x]
                }
                newMatrix[y, x] = sum
            }
        }

        return newMatrix
    }

    operator fun times(other: Vector): Vector {
        require(this.columns == other.dimension) {
            "cannot multiply matrix of size ($rows, $columns) with a vector of ${other.dimension} dimensions"
        }

        val newValues = DoubleArray(this.rows)

        for (y in 0 until this.rows) {
            var sum = 0.0
            for (x in 0 until this.columns) {
                sum += other[x] * this[y, x]
            }
            newValues[y] = sum
        }

        return newValues.toVector()
    }

    fun transposed(): Matrix {
        val newMatrix = Matrix(columns, rows, DoubleArray(rows * columns))

        for (y in 0 until rows) {
            for (x in 0 until columns) {
                newMatrix[x, y] = this[y, x]
            }
        }

        return newMatrix
    }

    override fun toString(): String = buildString {
        appendLine("Matrix($rows, $columns):")
        for (y in 0 until rows) {
            for (x in 0 until columns) {
                append("%6.2f".format(Locale.ENGLISH, get(y, x)))
            }
            if (y != rows - 1) appendLine()
        }
    }
}

fun matrixOf(rows: Int, columns: Int, vararg values: Double) = Matrix(rows, columns, values)

fun randomMatrix(rows: Int, columns: Int): Matrix {
    val random = Random()
    val values = DoubleArray(rows * columns) { random.nextGaussian() }
    return Matrix(rows, columns, values)
}