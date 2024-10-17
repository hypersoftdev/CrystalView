[![](https://jitpack.io/v/hypersoftdev/CrystalView.svg)](https://jitpack.io/#hypersoftdev/CrystalView)

# CrystalView

CrystalView

## Gradle Integration

### Step A: Add Maven Repository

In your project-level **build.gradle** or **settings.gradle** file, add the JitPack repository:
```
repositories {
    google()
    mavenCentral()
    maven { url "https://jitpack.io" }
}
```  

### Step B: Add Dependencies

In your app-level **build.gradle** file, add the library dependency. Use the latest version: [![](https://jitpack.io/v/hypersoftdev/CrystalView.svg)](https://jitpack.io/#hypersoftdev/CrystalView)

Groovy Version
```
 implementation 'com.github.hypersoftdev:CrystalView:x.x.x'
```
Kts Version
```
 implementation("com.github.hypersoftdev:CrystalView:x.x.x")
```

# How to use 

in XML

     <com.hypersoft.crystalview.views.BlurView
     android:id="@+id/blurView"
     android:layout_width="250dp"
     android:layout_height="250dp"
     app:blurOverlayColor="@color/colorOverlay"
     app:layout_constraintBottom_toBottomOf="parent"
     app:layout_constraintEnd_toEndOf="parent"
     app:layout_constraintStart_toStartOf="parent"
     app:layout_constraintTop_toTopOf="parent" />

in Java/Kotlin Class

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

## Screenshot

![Demo Image](assets/example.png)

# Acknowledgements

This work would not have been possible without the invaluable contributions of **Shafqat Hussain**. His expertise, dedication, and unwavering support have been instrumental in bringing this project to fruition.

We are deeply grateful for **Shafqat Hussain** involvement and his belief in the importance of this work. His contributions have made a significant impact, and we are honored to have had the opportunity to collaborate with him.

# LICENSE

Copyright 2023 Hypersoft Inc

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
