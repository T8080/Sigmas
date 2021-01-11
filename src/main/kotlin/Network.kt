import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import math.*
import network.*
import java.io.File

@Serializable
class Network(
    val layers: List<Layer>,
    val costFunction: CostFunction
) {
    fun feedForward(input: Vector): Vector {
        return layers.fold(input) { activation: Vector, layer: Layer ->
            layer.activationFunction.activate(layer.integrate(activation))
        }
    }

    fun getErrorGradients(input: Vector, correct: Vector): Pair<List<Vector>, List<Matrix>> {
        var activation: Vector = input
        val zs: MutableList<Vector> = mutableListOf()

        val activations: MutableList<Vector> = mutableListOf(activation)
        val biasGradients: MutableList<Vector> = mutableListOf()
        val weightGradients: MutableList<Matrix> = mutableListOf()

        for (layer in layers) {
            val z: Vector = layer.integrate(activation)
            zs.add(z)
            activation = layer.activationFunction.activate(z)
            activations.add(activation)
        }

        val cost: Vector = costFunction.derivative(activations.last(), correct)

        val error: Vector = cost elementProduct layers.last().activationFunction.derivative(zs.last())
        biasGradients.add(0, error)
        weightGradients.add(0, error * activations[activations.size - 2].transposed())

        for (l in layers.size - 2 downTo 0) {
            val z: Vector = zs[l]
            val activationDerivative: Vector = layers[l].activationFunction.derivative(z)
            val error: Vector = layers[l + 1].getPreviousLayerError(activationDerivative, biasGradients[0])

            biasGradients.add(0, error)
            weightGradients.add(0, error * activations[l].transposed())
        }

        return biasGradients to weightGradients
    }

    fun train(
        trainData: Dataset,
        epochs: Int,
        batchSize: Int,
        learningRate: Double
    ) {
        for (e in 0 until epochs) {
            println("epoch: $e")

            val batches: List<Dataset> = trainData.shuffled().windowed(batchSize)

            for (batch in batches) {
                val biasGradientAverage: List<Vector> = layers.map { Vector(it.outputSize) }
                val weightGradientAverage: List<Matrix> = layers.map { Matrix(it.inputSize, it.outputSize) }

                for ((x, y) in batch) {
                    val (biasGradients, weightGradients) = getErrorGradients(x, y)

                    for (l in biasGradientAverage.indices) {
                        biasGradientAverage[l] += biasGradients[l]
                        weightGradientAverage[l] += weightGradients[l]
                    }
                }

                for (l in biasGradientAverage.indices) {
                    biasGradientAverage[l] /= batch.size.toDouble()
                    weightGradientAverage[l] /= batch.size.toDouble()
                }

                for ((i, layer) in layers.withIndex()) {
                    layer.update(biasGradientAverage[i], weightGradientAverage[i], learningRate)
                }
            }
        }
    }

    fun save(path: String) {
        File(path).writeText(serializer.encodeToString(this))
    }

    companion object {
        val serializer = Json {
            serializersModule = SerializersModule {
                polymorphic(Layer::class) {
                    subclass(DenseLayer::class)
                }
            }
        }

        fun load(path: String): Network {
            return serializer.decodeFromString(File(path).readText())
        }
    }
}

typealias Dataset = List<TrainPair>
typealias TrainPair = Pair<Vector, Vector>