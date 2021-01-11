package network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.*

@Serializable
class DenseLayer(
    override val inputSize: Int,
    override val outputSize: Int
) : Layer () {

    var weights: Matrix = randomMatrix(rows = outputSize, columns = inputSize)
    var bias: Vector = randomVector(outputSize)

    override val activationFunction: ActivationFunction = ActivationFunction.Sigmoid

    override fun integrate(input: Vector): Vector {
        return weights * input + bias
    }

    override fun getPreviousLayerError(
        previousActivationDerivative: Vector,
        error: Vector
    ): Vector {
        return (weights.transposed() * error) elementProduct previousActivationDerivative
    }

    override fun update(
        biasGradient: Vector,
        weightGradient: Matrix,
        learningRate: Double
    ) {
        bias = (bias - learningRate*biasGradient)
        weights = (weights - learningRate*weightGradient)
    }
}
