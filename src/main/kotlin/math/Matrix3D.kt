package math

import kotlinx.serialization.Serializable

@Serializable
data class Matrix3D(
    val depth: Int,
    val rows: Int,
    val columns: Int,
    val elements: List<Matrix>
) {
    operator fun get(z: Int): Matrix = elements[z]
}