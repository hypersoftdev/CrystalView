package com.hypersoft.crystalview.algo

import android.graphics.Bitmap
import android.graphics.Canvas

interface BlurAlgorithm {
    fun blur(bitmap: Bitmap, blurRadius: Float): Bitmap?
    fun destroy()
    fun canModifyBitmap(): Boolean
    val supportedBitmapConfig: Bitmap.Config
    fun scaleFactor(): Float
    fun render(canvas: Canvas, bitmap: Bitmap)
}
