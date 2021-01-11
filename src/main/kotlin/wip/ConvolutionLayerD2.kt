package wip

import math.*
import network.*

/*    val filterDepth: Int,
    val filterRows: Int,
    val filterColumns: Int,*/

class ConvolutionLayerD2(
    val inputShape: Shape.D3,
    val kernelShape: Shape.D3,
    val kernelCount: Int,
    val stride: Int,
    val activationFunction: ActivationFunction,
    val kernels: List<Matrix3D>,
    val biases: List<Double>
) {

    val outputShape = Shape.D3(
        kernelCount,
        ((inputShape.rows - kernelShape.rows) / stride) + 1,
        ((inputShape.columns - kernelShape.columns) / stride) + 1
    )

    fun integrate(image: Matrix3D): Matrix3D {
        val kernelXOffset = kernelShape.rows / 2
        val kernelYOffset = kernelShape.columns / 2

        val output = outputShape.toMatrix()

        fun getConvolutionValue(kernel: Matrix3D, z: Int, outputY: Int, outputX: Int): Double {
            // convolution value for this position in the input image
            var aggregate = 0.0

            // loop over every position in the kernel
            for (kernelZ in 0 until kernelShape.depth) {
                for (kernelY in 0 until kernelShape.rows) {
                    for (kernelX in 0 until kernelShape.columns) {
                        // calculate kernel position in image

                        val imageY = outputY*stride + kernelY
                        val imageX = outputX*stride + kernelX

                        if (imageY !in 0 until inputShape.rows) continue
                        if (imageX !in 0 until inputShape.columns) continue

                        val imageValue = image[z][imageY, imageX]
                        val kernelValue = kernel[z][kernelY, kernelX]

                        aggregate += kernelValue * imageValue
                    }
                }
            }

            // activationFunction(aggregate) + bias[kernel]
            return aggregate
        }

        for (kernel in 0 until kernelCount) {

            // loop over every location in the output
            for (z in 0 until outputShape.depth) {
                for (y in 0 until outputShape.rows) {
                    for (x in 0 until outputShape.columns) {
                        val convolution = getConvolutionValue(kernels[kernel], z, y, x)
                        val activation = activationFunction(convolution + biases[kernel])
                        output[z][y, x] = activation
                    }
                }
            }
        }

        return output
    }

/*    fun stanceOut(y: Int, x: Int): Matrix3D {
    }*/

    /*

    [][][]
    [][][]
    [][][]

    [][]
    [][]
    *  def stanceOut(shape: (Int, Int), stride: Int): List[List[Matrix2D]] = {
            val elements: List[List[Double]] = matrix2D.elements

            val resultingShape: (Int, Int) = (
                ((matrix2D.rows - shape._1) / stride) + 1,
                ((matrix2D.cols - shape._2) / stride) + 1
            )
            var res: List[List[Matrix2D]] = Nil

            for (i <- 0 until resultingShape._1) {
                var resRow: List[Matrix2D] = Nil
                for (j <- 0 until resultingShape._2) {
                    var rows: List[List[Double]] = Nil
                    for (k <- (i * stride) until (i * stride) + shape._1) {
                        var cols: List[Double] = Nil
                        for (l <- (j * stride) until (j * stride) + shape._2) {
                            cols = cols :+ elements(k)(l)
                        }
                        rows = rows :+ cols
                    }
                    resRow = resRow :+ Matrix2D(rows)
                }
                res = res :+ resRow
            }

            res
        }
        * */
}