package com.hypersoft.crystalview.views

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

interface BlurViewFacade {

    fun setBlurEnabled(enabled: Boolean): BlurViewFacade

    fun setBlurAutoUpdate(enabled: Boolean): BlurViewFacade

    fun setFrameClearDrawable(frameClearDrawable: Drawable?): BlurViewFacade

    fun setBlurRadius(radius: Float): BlurViewFacade

    fun setOverlayColor(@ColorInt overlayColor: Int): BlurViewFacade
}
