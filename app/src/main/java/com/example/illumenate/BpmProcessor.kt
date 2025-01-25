package com.example.illumenate

class BpmProcessor(
    private val callback: (Float, Float) -> Unit,
    private val minBpm: Float,
    private val maxBpm: Float
) {
    // Temporary implementation until TarsosDSP is properly integrated
    fun process(data: ByteArray): Float {
        // Return a default value for now
        return 120f
    }
}
