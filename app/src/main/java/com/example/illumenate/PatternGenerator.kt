package com.example.illumenate

object PatternGenerator {
    fun createPattern(bpm: Float, intensity: Float = 0.5f): LongArray {
        val baseInterval = (60000 / bpm).toLong() // ms per beat
        
        return when {
            bpm < 80 -> createMeditativePattern(baseInterval)
            bpm < 120 -> createNormalPattern(baseInterval)
            else -> createEnergizedPattern(baseInterval)
        }
    }

    private fun createMeditativePattern(baseInterval: Long): LongArray {
        return longArrayOf(
            baseInterval, // ON
            baseInterval / 2 // OFF
        )
    }

    private fun createNormalPattern(baseInterval: Long): LongArray {
        return longArrayOf(
            baseInterval / 2, // ON
            baseInterval / 4  // OFF
        )
    }

    private fun createEnergizedPattern(baseInterval: Long): LongArray {
        return longArrayOf(
            baseInterval / 4, // Quick ON
            baseInterval / 4  // Quick OFF
        )
    }
}