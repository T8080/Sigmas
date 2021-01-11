package network

import kotlinx.serialization.Serializable
import math.*

@Serializable
sealed class Shape(
    val total: Int
) {

    data class D1(val size: Int) : Shape(size)

    data class D2(val rows: Int, val columns: Int): Shape(rows * columns)

    data class D3(
        val depth: Int,
        val rows: Int,
        val columns: Int
    ): Shape(0) {
        fun toMatrix() = Matrix3D(depth, rows, columns, List(depth) { Matrix(rows, columns) })
    }
}