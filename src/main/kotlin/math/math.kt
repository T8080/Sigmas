import math.*
import kotlin.math.*

fun sigmoid(z: Double): Double =
    1.0 / (1.0 + exp(-z))

fun sigmoidPrime(z: Double): Double =
    sigmoid(z) * (1 - sigmoid(z))

fun sigmoidVec(z: Vector): Vector =
    z.mapElements(::sigmoid)

fun sigmoidPrimeVec(z: Vector): Vector =
    z.mapElements(::sigmoidPrime)