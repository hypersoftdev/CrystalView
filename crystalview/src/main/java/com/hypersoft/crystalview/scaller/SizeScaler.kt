package com.hypersoft.crystalview.scaller

import kotlin.math.ceil


class SizeScaler(private val scaleFactor: Float) {
    fun scale(width: Int, height: Int): Size {
        val nonRoundedScaledWidth = downscaleSize(width.toFloat())
        val scaledWidth = roundSize(nonRoundedScaledWidth)
        //Only width has to be aligned to ROUNDING_VALUE
        val roundingScaleFactor = width.toFloat() / scaledWidth
        //Ceiling because rounding or flooring might leave empty space on the View's bottom
        val scaledHeight = ceil((height / roundingScaleFactor).toDouble()).toInt()

        return Size(scaledWidth, scaledHeight, roundingScaleFactor)
    }

    fun isZeroSized(measuredWidth: Int, measuredHeight: Int): Boolean {
        return downscaleSize(measuredHeight.toFloat()) == 0 || downscaleSize(measuredWidth.toFloat()) == 0
    }

    private fun roundSize(value: Int): Int {
        if (value % ROUNDING_VALUE == 0) {
            return value
        }
        return value - (value % ROUNDING_VALUE) + ROUNDING_VALUE
    }

    private fun downscaleSize(value: Float): Int {
        return ceil((value / scaleFactor).toDouble()).toInt()
    }

    class Size internal constructor(val width: Int, val height: Int, private val scaleFactor: Float) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false

            val size = other as Size

            if (width != size.width) return false
            if (height != size.height) return false
            return java.lang.Float.compare(size.scaleFactor, scaleFactor) == 0
        }

        override fun hashCode(): Int {
            var result = width
            result = 31 * result + height
            result = 31 * result + (if (scaleFactor != +0.0f) java.lang.Float.floatToIntBits(
                scaleFactor
            ) else 0)
            return result
        }

        override fun toString(): String {
            return "Size{" +
                    "width=" + width +
                    ", height=" + height +
                    ", scaleFactor=" + scaleFactor +
                    '}'
        }
    }

    companion object {
        private const val ROUNDING_VALUE = 64
    }
}
