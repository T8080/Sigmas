import math.Vector
import network.CostFunction
import network.DenseLayer

fun main() {
    val network = Network.load("95.network")

    testNetwork(network)
}

fun trainNetwork() {
    val network = Network(
        layers = listOf(
            DenseLayer(28 * 28, 30),
            DenseLayer(30, 10)
        ),
        costFunction = CostFunction.CrossEntropy
    )

    val trainData = readMnistDataset().take(10_000)

    network.train(trainData, 30, 10, 0.5)

    network.save("new.network")
}


fun testNetwork(network: Network) {
    val testData = readMnistDataset(testData = true)

    val testCount = 100
    var accuracy = 0.0

    for (i in 0 until testCount) {
        val (x, y) = testData[i]
        val predicted = network.feedForward(x)

        if (y.maxIndex() == predicted.maxIndex()) accuracy += 1.0

        println(x.imageString())
        println("correct: ${y.maxIndex()}: $y")
        println("predicted: ${predicted.maxIndex()}: $predicted")
    }

    accuracy /= testCount
    println("accuracy: $accuracy")
}

fun Vector.maxIndex(): Int {
    return elements.withIndex().maxByOrNull { it.value }!!.index
}