package com.hypersoft.crystalview.controllers

import android.graphics.Canvas
import com.hypersoft.crystalview.views.BlurViewFacade

interface BlurController : BlurViewFacade {
    fun draw(canvas: Canvas?): Boolean
    fun updateBlurViewSize()
    fun destroy()
    companion object {
        const val DEFAULT_SCALE_FACTOR: Float = 6f
        const val DEFAULT_BLUR_RADIUS: Float = 16f
    }
}
