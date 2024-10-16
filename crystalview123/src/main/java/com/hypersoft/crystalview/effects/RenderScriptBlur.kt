package com.hypersoft.crystalview.effects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.hypersoft.crystalview.algo.BlurAlgorithm
import com.hypersoft.crystalview.controllers.BlurController

@Deprecated(
    """because RenderScript is deprecated and its hardware acceleration is not guaranteed.
  RenderEffectBlur is the best alternative at the moment."""
)
class RenderScriptBlur(context: Context) : BlurAlgorithm {
    private val paint = Paint(Paint.FILTER_BITMAP_FLAG)
    private val renderScript: RenderScript = RenderScript.create(context)
    private val blurScript: ScriptIntrinsicBlur =
        ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    private var outAllocation: Allocation? = null

    private var lastBitmapWidth = -1
    private var lastBitmapHeight = -1

    private fun canReuseAllocation(bitmap: Bitmap): Boolean {
        return bitmap.height == lastBitmapHeight && bitmap.width == lastBitmapWidth
    }

    override fun blur(bitmap: Bitmap, blurRadius: Float): Bitmap? {
        //Allocation will use the same backing array of pixels as bitmap if created with USAGE_SHARED flag
        val inAllocation = Allocation.createFromBitmap(renderScript, bitmap)

        if (!canReuseAllocation(bitmap)) {
            if (outAllocation != null) {
                outAllocation!!.destroy()
            }
            outAllocation = Allocation.createTyped(renderScript, inAllocation.type)
            lastBitmapWidth = bitmap.width
            lastBitmapHeight = bitmap.height
        }

        blurScript.setRadius(blurRadius)
        blurScript.setInput(inAllocation)
        //do not use inAllocation in forEach. it will cause visual artifacts on blurred Bitmap
        blurScript.forEach(outAllocation)
        outAllocation!!.copyTo(bitmap)

        inAllocation.destroy()
        return bitmap
    }

    override fun destroy() {
        blurScript.destroy()
        renderScript.destroy()
        if (outAllocation != null) {
            outAllocation!!.destroy()
        }
    }

    override fun canModifyBitmap(): Boolean {
        return true
    }

    override val supportedBitmapConfig: Bitmap.Config
        get() = Bitmap.Config.ARGB_8888

    override fun scaleFactor(): Float {
        return BlurController.DEFAULT_SCALE_FACTOR
    }

    override fun render(canvas: Canvas, bitmap: Bitmap) {
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}
