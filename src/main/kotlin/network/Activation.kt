package network

import math.*
import sigmoid
import sigmoidPrime
import kotlinx.serialization.Serializable

@Serializable
sealed class ActivationFunction {
    abstract val activate: Function
    abstract val derivative: Function

    operator fun invoke(x: Double): Double = activate.invoke(x)

    @Serializable
    object Sigmoid : ActivationFunction() {
        override val activate: Function = ::sigmoid
        override val derivative: Function = ::sigmoidPrime
    }

    @Serializable
    object Linear : ActivationFunction() {
        override val activate: Function = { it }
        override val derivative: Function = { 0.0 }
    }
}

typealias Function = (Double) -> Double

operator fun Function.invoke(x: Vector): Vector {
    return x.mapElements(this::invoke)
}