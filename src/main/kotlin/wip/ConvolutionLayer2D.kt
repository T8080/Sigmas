package wip

class ConvolutionLayer2D(
    val inputShape: IntArray,
    val filterCount: Int
) {
    val filters = DoubleTensor(inputShape.addDimension(filterCount))
}

typealias NDShape = IntArray

fun NDShape.addDimension(size: Int): NDShape {
    val newShape = IntArray(size + 1)
    newShape[0] = size
    System.arraycopy(this, 0, newShape, 1, size)
    return newShape
}