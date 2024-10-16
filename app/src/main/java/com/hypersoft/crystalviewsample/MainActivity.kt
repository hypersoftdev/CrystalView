package com.hypersoft.crystalviewsample

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.hypersoft.crystalview.algo.BlurAlgorithm
import com.hypersoft.crystalview.consts.ConsUtils.minBlurRadius
import com.hypersoft.crystalview.consts.ConsUtils.radius
import com.hypersoft.crystalview.consts.ConsUtils.step
import com.hypersoft.crystalview.effects.RenderEffectBlur
import com.hypersoft.crystalview.effects.RenderScriptBlur
import com.hypersoft.crystalviewsample.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBlurView()
    }

    private fun setupBlurView() {

        // set background, if your root layout doesn't have one
        val windowBackground = window.decorView.background
        val algorithm: BlurAlgorithm = getBlurAlgorithm()

        binding.blurView.setupWith(binding.root, algorithm).setFrameClearDrawable(windowBackground)
            .setBlurRadius(
                radius
            )

        binding.blurView.setupWith(binding.root, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBackground).setBlurRadius(radius)

        val initialProgress = (radius * step).toInt()
        binding.radiusSeekBar.progress = initialProgress

        binding.radiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var blurRadius = progress / step
                blurRadius = max(blurRadius.toDouble(), minBlurRadius.toDouble()).toFloat()
                Log.d("seekbar_progress", "Progress: $progress  ::  blurRadius: $blurRadius")

                binding.blurView.setBlurRadius(blurRadius)
                binding.blurView.setBlurRadius(blurRadius)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

    }

    private fun getBlurAlgorithm(): BlurAlgorithm {
        val algorithm: BlurAlgorithm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffectBlur()
        } else {
            RenderScriptBlur(this)
        }
        return algorithm
    }


}