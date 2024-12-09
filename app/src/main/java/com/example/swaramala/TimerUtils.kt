package com.example.swaramala

import android.os.Handler
import android.os.Looper

class TimerUtils {
    companion object {
        fun setTimeout(callback: () -> Unit, delay: Long) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    // TODO Play sound on click of button.
                    callback()
                },
                delay // value in milliseconds
            )
        }
    }
}