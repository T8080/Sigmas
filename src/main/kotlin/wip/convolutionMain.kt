package wip

import math.*
import network.*

fun main() {
    val imageLayer = matrixOf(
        4, 4,
        1.0, 1.0, 1.0, 0.0,
        0.0, 1.0, 1.0, 1.0,
        0.0, 0.0, 1.0, 1.0,
        0.0, 0.0, 1.0, 1.0
    )
    val image3D = Matrix3D(2, 4, 4, listOf(imageLayer, imageLayer))

    val kernel = matrixOf(
        3, 3,
        1.0, 0.0, 1.0,
        0.0, 1.0, 0.0,
        1.0, 0.0, 1.0
    )
    val kernel3D = Matrix3D(2, 3, 3, listOf(kernel, kernel))

    val layer = ConvolutionLayerD2(
        inputShape = Shape.D3(2, 4, 4),
        kernelShape = Shape.D3(2, 3, 3),
        kernelCount = 1,
        stride = 1,
        activationFunction = ActivationFunction.Sigmoid,
        kernels = listOf(kernel3D),
        biases = listOf(0.0)
    )

    val x = layer.integrate(image3D)

    println(x[0])
}