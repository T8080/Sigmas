import math.*
import network.DenseLayer
import kotlin.random.Random

fun main() {
  /*  val network = Network(listOf(28 * 28, 30, 10))

    val trainData = readMnistDataset().take(10_000)
    val testData = readMnistDataset(testData = true)

    network.trainSGD(trainData, 100, 100, 0.02)

    val testCount = 10
    var accuracy = 0.0

    for (i in 0 until testCount) {
        val (x, y) = trainData[i]
        val predicted = network.feedforward(x)

        if (y.maxIndex() == predicted.maxIndex()) accuracy += 1.0

        println(x.imageString())
        println("correct: ${y.maxIndex()}: $y")
        println("predicted: ${predicted.maxIndex()}: $predicted")
        println()
    }

    accuracy /= testCount
    println("accuracy: $accuracy")*/
}

fun simpleNetwork() {
   /* val network = Network(listOf(1, 1))

    val data = List(1000) {
        Pair(vectorOf(Random.nextDouble()), vectorOf(0.0))
    }

    network.trainSGD(data, 100, 10, 1.0)

    for (x in 0 until 10) {
        println(network.feedforward(vectorOf(Random.nextDouble())))
    }*/
}

fun andNetwork() {
  /*  val network = Network(listOf(2, 2, 1))

    val trainData = List(1000) { andTestSet() }
    val testData = List(100) { andTestSet() }

    network.trainSGD(trainData, 100, 100, 1.0)

    println(network.feedforward(vectorOf(0.0, 0.0)))
    println(network.feedforward(vectorOf(1.0, 0.0)))
    println(network.feedforward(vectorOf(0.0, 1.0)))
    println(network.feedforward(vectorOf(1.0, 1.0)))

    for (w in network.layerWeights) {
        println(w)
    }
    for (b in network.layerBiases) {
        println(b)
    }*/
}

fun xorNetwork() {
   /* val network = Network(listOf(2, 2, 1))

    val trainData = List(1000) { xorTestSet() }
    val testData = List(100) { xorTestSet() }

    network.trainSGD(trainData, 100, 100, 1.0)

    println(network.feedforward(vectorOf(0.0, 0.0)))
    println(network.feedforward(vectorOf(1.0, 0.0)))
    println(network.feedforward(vectorOf(0.0, 1.0)))
    println(network.feedforward(vectorOf(1.0, 1.0)))

    for (w in network.layerWeights) {
        println(w)
    }
    for (b in network.layerBiases) {
        println(b)
    }*/
}


fun andTestSet(): Pair<Vector, Vector> {
    val input = vectorOf(
        setOf(0.0, 1.0).random(),
        setOf(0.0, 1.0).random()
    )

    val output = vectorOf(if (input[0] == 1.0 && input[1] == 1.0) 1.0 else 0.0)

    return Pair(input, output)
}

fun xorTestSet(): Pair<Vector, Vector> {
    val input = vectorOf(
        setOf(0.0, 1.0).random(),
        setOf(0.0, 1.0).random()
    )

    val output = if (input[0] != input[1]) vectorOf(1.0) else vectorOf(0.0)

    return Pair(input, output)
}