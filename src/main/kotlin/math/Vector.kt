package math

import java.util.*
import kotlinx.serialization.Serializable

@Serializable
class Vector(
    override val elements: DoubleArray
): ElementOperations<Vector> {
    constructor(dimension: Int) : this(DoubleArray(dimension))

    val dimension: Int get() = elements.size

//    override val shape: Shape get() = Shape.D1(dimension)

    override fun copy(elements: DoubleArray): Vector {
        return Vector(elements)
    }

    operator fun get(index: Int): Double =
        elements[index]

    operator fun times(other: Vector): Double {
        this shouldMatchDimension other

        var sum = 0.0

        for (i in 0 until dimension) {
            sum += this[i] * other[i]
        }

        return sum
    }

    fun toMatrix(): Matrix =
        Matrix(dimension, 1, elements)

    fun transposed(): Matrix =
        Matrix(1, dimension, elements)

    operator fun times(other: Matrix): Matrix =
        this.toMatrix() * other


    override fun toString(): String =
        elements.joinToString(", ", "Vector(", ")") { "%.2f".format(Locale.ENGLISH, it) }
}

fun vectorOf(vararg values: Double) = Vector(values)

fun DoubleArray.toVector() = Vector(this)

fun randomVector(dimension: Int): Vector {
    val rand = Random()
    return Vector(dimension) { rand.nextGaussian() }
}

inline fun Vector(dimension: Int, init: (Int) -> Double): Vector {
    val values = DoubleArray(dimension)
    for (i in 0 until dimension) {
        values[i] = init(i)
    }
    return Vector(values)
}

private infix fun Vector.shouldMatchDimension(other: Vector) {
    require(this.dimension == other.dimension) { "vectors do not have the same dimension" }
}