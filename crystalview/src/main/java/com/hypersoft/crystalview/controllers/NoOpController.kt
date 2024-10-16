package com.hypersoft.crystalview.controllers

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.hypersoft.crystalview.views.BlurViewFacade

class NoOpController : BlurController {

    override fun draw(canvas: Canvas?): Boolean {
        return true
    }

    override fun updateBlurViewSize() {}

    override fun destroy() {}

    override fun setBlurRadius(radius: Float): BlurViewFacade {
        return this
    }

    override fun setOverlayColor(overlayColor: Int): BlurViewFacade{
        return this
    }

    override fun setFrameClearDrawable(windowBackground: Drawable?): BlurViewFacade {
        return this
    }

    override fun setBlurEnabled(enabled: Boolean): BlurViewFacade {
        return this
    }

    override fun setBlurAutoUpdate(enabled: Boolean): BlurViewFacade {
        return this
    }
}
