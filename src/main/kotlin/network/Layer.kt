package network

import math.*
import kotlinx.serialization.Serializable

@Serializable
abstract class Layer {
    abstract val inputSize: Int
    abstract val outputSize: Int

    abstract val activationFunction: ActivationFunction

    abstract fun integrate(input: Vector): Vector

    /**
     * Calculate the errors of the previous layer using back propagation
     *
     * @param previousActivationDerivative: derivative of the previous activation; f_{l-1}'(z(l-1))
     * @param error: the error in this layer; δ(l)
     * @return the error of the previous layer; δ(l-1)
     */
    abstract fun getPreviousLayerError(
        previousActivationDerivative: Vector,
        error: Vector
    ): Vector

    /**
     * Update the parameters of this layer.
     *
     * @param previousLayerActivation: previous layer activation; f_{l-1}(z(l-1))
     * @param error: error vector for this layer, should be calculated by SGD or similar; δ(l)
     * @param learningRate: weight of the new error vector; η
     * */
    abstract fun update(
        biasGradient: Vector,
        weightGradient: Matrix,
        learningRate: Double
    )
}