package com.hypersoft.crystalview.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import com.hypersoft.crystalview.R
import com.hypersoft.crystalview.algo.BlurAlgorithm
import com.hypersoft.crystalview.controllers.BlurController
import com.hypersoft.crystalview.controllers.NoOpController
import com.hypersoft.crystalview.controllers.PreDrawBlurController
import com.hypersoft.crystalview.effects.RenderEffectBlur
import com.hypersoft.crystalview.effects.RenderScriptBlur

class BlurView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = BlurView::class.java.simpleName
    }

    private var blurController: BlurController = NoOpController()

    @ColorInt
    private var overlayColor: Int = android.graphics.Color.TRANSPARENT

    init {
        val a: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BlurView, defStyleAttr, 0)
        overlayColor =
            a.getColor(R.styleable.BlurView_blurOverlayColor, android.graphics.Color.TRANSPARENT)
        a.recycle()
    }

    override fun draw(canvas: Canvas) {
        if (blurController.draw(canvas)) {
            super.draw(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        blurController.updateBlurViewSize()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        blurController.setBlurAutoUpdate(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isHardwareAccelerated) {
            Log.e(TAG, "BlurView can't be used in a non-hardware-accelerated window!")
        } else {
            blurController.setBlurAutoUpdate(true)
        }
    }

    /**
     * @param rootView Root view to start blur from. Can be the Activity's root content (android.R.id.content) or another layout.
     * @param algorithm Sets the blur algorithm.
     * @return BlurViewFacade to set additional parameters.
     */
    fun setupWith(@NonNull rootView: ViewGroup, algorithm: BlurAlgorithm): BlurViewFacade {
        blurController.destroy()
        blurController = PreDrawBlurController(this, rootView, overlayColor, algorithm)
        return blurController
    }

    /**
     * Automatically picks the blur algorithm based on the API version.
     * Uses RenderEffectBlur on API 31+, and RenderScriptBlur on older versions.
     */
    fun setupWith(@NonNull rootView: ViewGroup): BlurViewFacade {
        return setupWith(rootView, getBlurAlgorithm())
    }

    /**
     * Set blur radius.
     */
    fun setBlurRadius(radius: Float): BlurViewFacade {
        return blurController.setBlurRadius(radius)
    }

    /**
     * Set overlay color.
     */
    fun setOverlayColor(@ColorInt overlayColor: Int): BlurViewFacade {
        this.overlayColor = overlayColor
        return blurController.setOverlayColor(overlayColor)
    }

    /**
     * Enable or disable automatic blur updates.
     */
    fun setBlurAutoUpdate(enabled: Boolean): BlurViewFacade {
        return blurController.setBlurAutoUpdate(enabled)
    }

    /**
     * Enable or disable the blur effect.
     */
    fun setBlurEnabled(enabled: Boolean): BlurViewFacade {
        return blurController.setBlurEnabled(enabled)
    }

    @NonNull
    private fun getBlurAlgorithm(): BlurAlgorithm {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffectBlur()
        } else {
            RenderScriptBlur(context)
        }
    }
}
