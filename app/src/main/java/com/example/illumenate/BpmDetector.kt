package com.example.illumenate
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BpmDetector(private val context: Context) {
    suspend fun analyze(uri: Uri): Float = withContext(Dispatchers.IO) {
        // Temporary implementation
        120f
    }
}