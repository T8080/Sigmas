package wip

import math.*

/*
abstract class Layer2<In, Out>(
    val inputShape: Shape,
    val outputShape: Shape
    ) {
    abstract val activationFunction: ActivationFunction

    abstract fun integrate(input: In): Out

    */
/**
     * Calculate the errors of the previous layer using back propagation
     *
     * @param previousActivationDerivative: derivative of the previous activation; f_{l-1}'(z(l-1))
     * @param error: the error in this layer; δ(l)
     * @return the error of the previous layer; δ(l-1)
     *//*

    abstract fun getPreviousLayerError(
        previousActivationDerivative: Out,
        error: Out
    ): In

    */
/**
     * Update the parameters of this layer.
     *
     * @param previousLayerActivation: previous layer activation; f_{l-1}(z(l-1))
     * @param error: error vector for this layer, should be calculated by SGD or similar; δ(l)
     * @param learningRate: weight of the new error vector; η
     * *//*

    abstract fun update(
        biasGradient: Out,
        weightGradient: Matrix,
        learningRate: Double
    )
}*/
