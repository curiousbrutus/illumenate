package com.example.illumenate

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.Looper
import android.util.Log

class FlashController(context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null
    private var currentHandler: Handler? = null

    init {
        cameraId = cameraManager.cameraIdList.firstOrNull()
    }

    fun startPattern(pattern: LongArray) {
        stopPattern()
        val handler = Handler(Looper.getMainLooper())
        var patternIndex = 0

        val runnable = object : Runnable {
            override fun run() {
                if (patternIndex >= pattern.size) patternIndex = 0
                val duration = pattern[patternIndex]
                toggleFlash(patternIndex % 2 == 0)
                handler.postDelayed(this, duration)
                patternIndex++
            }
        }

        handler.post(runnable)
        currentHandler = handler
    }

    fun stopPattern() {
        currentHandler?.removeCallbacksAndMessages(null)
        toggleFlash(false)
    }

    private fun toggleFlash(enable: Boolean) {
        try {
            cameraId?.let { cameraManager.setTorchMode(it, enable) }
        } catch (e: Exception) {
            Log.e("FlashController", "Flash error: ${e.message}")
        }
    }
}