package com.example.illumenate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.illumenate.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var flashController: FlashController
    private lateinit var bpmDetector: BpmDetector
    private lateinit var musicPlayer: ExoPlayer
    private var currentBpm = 120f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        flashController = FlashController(this)
        bpmDetector = BpmDetector(this)
        musicPlayer = ExoPlayer.Builder(this).build()

        binding.btnSelectMusic.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            startActivityForResult(intent, MUSIC_REQUEST_CODE)
        }

        binding.btnStart.setOnClickListener {
            lifecycleScope.launch {
                binding.loadingProgress.visibility = View.VISIBLE
                val pattern = PatternGenerator.createPattern(currentBpm)
                flashController.startPattern(pattern)
                musicPlayer.play()
                binding.loadingProgress.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MUSIC_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                lifecycleScope.launch {
                    binding.loadingProgress.visibility = View.VISIBLE
                    currentBpm = bpmDetector.analyze(uri)
                    binding.tvBpm.text = "Detected BPM: %.1f".format(currentBpm)
                    musicPlayer.setMediaItem(MediaItem.fromUri(uri))
                    musicPlayer.prepare()
                    binding.loadingProgress.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        flashController.stopPattern()
        musicPlayer.release()
    }

    companion object {
        const val MUSIC_REQUEST_CODE = 1001
    }
}