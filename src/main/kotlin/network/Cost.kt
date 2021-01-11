package network

import math.Vector
import math.combineElements
import math.mapElements
import kotlin.math.pow
import kotlinx.serialization.Serializable
import sigmoid
import kotlin.math.ln

@Serializable
sealed class CostFunction {
    abstract val cost: BiFunction
    abstract val derivative: BiFunction

    operator fun invoke(x: Double, y: Double): Double = cost.invoke(x, y)

    @Serializable
    object SquaredSum : CostFunction() {
        override val cost: BiFunction =  { x, y -> (x - y).pow(2.0) }
        override val derivative: BiFunction = { x, y -> x - y }
    }

    @Serializable
    object CrossEntropy : CostFunction() {
        override val cost: BiFunction = { x, y -> -(y*ln(x) + (1.0 - y)*ln(1.0 - x)) }
        override val derivative: BiFunction = { x, y -> -(y/x) + (1.0-y)/(1.0-x) }
    }

}
typealias BiFunction = (Double, Double) -> Double

operator fun BiFunction.invoke(x: Vector, y: Vector): Vector =
    x.combineElements(y, this)