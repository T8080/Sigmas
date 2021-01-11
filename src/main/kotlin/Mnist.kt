import math.*
import java.io.*
import java.util.zip.*

const val trainImages = "train-images-idx3-ubyte.gz"
const val trainLabels = "train-labels-idx1-ubyte.gz"
const val testImages = "t10k-images-idx3-ubyte.gz"
const val testLabels = "t10k-labels-idx1-ubyte.gz"

fun readImages(path: String): Sequence<Vector> = sequence {
    val reader = DataInputStream(GZIPInputStream(FileInputStream(path)))

    with(reader) {
        val magic = readInt()
        val count = readInt()
        val rows = readInt()
        val columns = readInt()
        val size = rows * columns
        val buffer = ByteArray(size)

        repeat(count) {
            reader.readFully(buffer)
            val image = DoubleArray(buffer.size) { i -> buffer[i].toUByte().toDouble() / 255.0 }
            yield(Vector(image))
        }
    }
}

fun readLabels(path: String): Sequence<Int> = sequence {
    val reader = DataInputStream(GZIPInputStream(FileInputStream(path)))

    with(reader) {
        val magic = readInt()
        val count = readInt()

        repeat(count) {
            yield(readUnsignedByte())
        }
    }
}

fun readMnistDataset(testData: Boolean = false): Dataset {
    val images: Sequence<Vector> = readImages(if (testData) testImages else trainImages)

    val labels: Sequence<Vector> = readLabels(if (testData) testLabels else trainLabels)
        .map { label ->
            Vector(10) { i -> if (i == label) 1.0 else 0.0 }
        }

    return (images zip labels).toList()
}

fun main() {
    val img = readImages(trainImages).toList()[2]
    val l = readLabels(trainLabels).toList()[2]
    println(img.imageString())
    println(l)

    val data = readMnistDataset()
    val (img1, label1) = data[10]

    println(img.imageString())
    println(label1)
}

fun Vector.imageString() = buildString {

    for (y in 0 until 28) {
        for (x in 0 until 28) {

            append("%2.0f".format((this@imageString[y * 28 + x] * 10.0)))
        }
        appendLine()
    }
}