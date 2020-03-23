package com.wesleydonk.keyboardtransition

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setDecorFitsSystemWindows(false)
        setContentView(R.layout.activity_main)

        val motionLayout = findViewById<MotionLayout>(R.id.edit_text_container)

        val callback = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(
                insets: WindowInsets,
                animations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {
                val isOpening = insets.isVisible(WindowInsets.Type.ime())

                // When the ime animation is finished, it's not part of the animations list anymore
                //  so we fallback to 1
                val fraction = animations.maxBy { animation -> animation.fraction }?.fraction ?: 1F
                val progress = when {
                    !isOpening -> 1F - fraction
                    else -> fraction
                }
                motionLayout.progress = progress
                return insets
            }
        }

        motionLayout.setWindowInsetsAnimationCallback(callback)
    }
}
