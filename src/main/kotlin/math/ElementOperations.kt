package math

import network.*

interface NDArray<T> {
    val shape: Shape

    operator fun plus(other: NDArray<T>): T
    operator fun plus(other: Double): T
}

interface ElementOperations<T> {
    val elements: DoubleArray

    fun copy(elements: DoubleArray): T

/*    override fun plus(other: NDArray<T>): T =
        combineElements(other as ElementOperations<*>) { a, b -> a + b }

    override fun plus(other: Double): T =
        mapElements { it + other }*/
}

inline fun <T> ElementOperations<T>.mapElements(mapper: (Double) -> Double): T {
    val newValues = DoubleArray(elements.size)

    for (i in elements.indices) {
        newValues[i] = mapper(elements[i])
    }

    return copy(newValues)
}

inline fun <T> ElementOperations<T>.mapElementsInPlace(mapper: (Double) -> Double) {
    for (i in elements.indices) {
        elements[i] = mapper(elements[i])
    }
}

inline fun <T> ElementOperations<T>.combineElements(other: ElementOperations<*>, combiner: (Double, Double) -> Double): T {
    require(this.elements.size == other.elements.size) { "arrays must have same size for element wise operations" }

    val newValues = DoubleArray(elements.size)

    for (i in elements.indices) {
        newValues[i] = combiner(this.elements[i], other.elements[i])
    }

    return copy(newValues)
}

inline fun <T> ElementOperations<T>.combineElementsInPlace(
    other: ElementOperations<T>,
    combiner: (Double, Double) -> Double
) {
    require(this.elements.size == other.elements.size) { "arrays must have same size for element wise operations" }

    for (i in elements.indices) {
        elements[i] = combiner(elements[i], other.elements[i])
    }
}

operator fun <T> ElementOperations<T>.plus(x: Double): T =
    mapElements { it + x }

operator fun <T> ElementOperations<T>.minus(x: Double): T =
    mapElements { it - x }

operator fun <T> ElementOperations<T>.times(x: Double): T =
    mapElements { it * x }

operator fun <T> Double.times(x: ElementOperations<T>): T =
    x.mapElements { it * this }

operator fun <T> ElementOperations<T>.div(x: Double): T =
    mapElements { it / x }

operator fun <T> ElementOperations<T>.plus(other: ElementOperations<T>): T =
    combineElements(other) { a, b -> a + b }

operator fun <T> ElementOperations<T>.minus(other: ElementOperations<T>): T =
    combineElements(other) { a, b -> a - b }

infix fun <T> ElementOperations<T>.elementProduct(other: ElementOperations<T>): T =
    combineElements(other) { a, b -> a * b }

operator fun <T> ElementOperations<T>.plusAssign(x: Double) =
    mapElementsInPlace { it + x }

operator fun <T> ElementOperations<T>.minusAssign(x: Double) =
    mapElementsInPlace { it - x }

operator fun <T> ElementOperations<T>.timesAssign(x: Double) =
    mapElementsInPlace { it * x }

operator fun <T> ElementOperations<T>.divAssign(x: Double) =
    mapElementsInPlace { it / x }

operator fun <T> ElementOperations<T>.plusAssign(other: ElementOperations<T>) =
    combineElementsInPlace(other) { a, b -> a + b }

operator fun <T> ElementOperations<T>.minusAssign(other: ElementOperations<T>) =
    combineElementsInPlace(other) { a, b -> a - b }

operator fun <T> ElementOperations<T>.timesAssign(other: ElementOperations<T>) =
    combineElementsInPlace(other) { a, b -> a * b }

operator fun <T> ElementOperations<T>.divAssign(other: ElementOperations<T>) =
    combineElementsInPlace(other) { a, b -> a / b }
