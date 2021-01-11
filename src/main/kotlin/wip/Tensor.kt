package wip
interface Tensor<T> {
    val shape: IntArray

    operator fun get(position: IntArray): T

    operator fun set(position: IntArray, value: Double)
}

class DoubleTensor(
    override val shape: IntArray
) : Tensor<Double> {
    val elements: DoubleArray = DoubleArray(shape.sum())

    private fun IntArray.toIndex(): Int {
        var index = 0

        for (i in indices) {
            index += shape[i] * get(i)
        }

        return index
    }

    override fun get(position: IntArray): Double {
        return elements[position.toIndex()]
    }

    override fun set(position: IntArray, value: Double) {
        elements[position.toIndex()] = value
    }
}