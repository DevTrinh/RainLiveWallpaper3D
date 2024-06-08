package com.mtkg.rainlivewallpaper

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mtkg.rainlivewallpaper.ads.InterstitialActivity
import com.mtkg.rainlivewallpaper.databinding.ActivitySetWallpaperBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class SetWallpaperActivity : AppCompatActivity() {

    private var videoPath: String = " "
    private val SET_WALLPAPER_REQUEST_CODE = 100
    private lateinit var binding: ActivitySetWallpaperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoPath = intent.getStringExtra("firstVideo").toString()

        val position = intent.getIntExtra("position", 0)
   //     removeTheme()
        val intent = Intent(
            this@SetWallpaperActivity, LiveWallpaperService::class.java
        )
        intent.putExtra("vediostring", videoPath)
        startService(intent)
        videoPlay()

        when (position) {

            0 -> {

                binding.setWallpaperBtn.visibility = View.VISIBLE
                binding.watchAdBtn.visibility = View.GONE
            }

            1 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            2 -> {
                binding.watchAdBtn.visibility = View.GONE
                binding.setWallpaperBtn.visibility = View.VISIBLE
            }

            3 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            4 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            5 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            6 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            7 -> {
                binding.watchAdBtn.visibility = View.GONE
                binding.setWallpaperBtn.visibility = View.VISIBLE
            }

            8 -> {
                binding.setWallpaperBtn.visibility = View.GONE
                binding.watchAdBtn.visibility = View.VISIBLE
            }

            9 -> {
                binding.watchAdBtn.visibility = View.GONE
                binding.setWallpaperBtn.visibility = View.VISIBLE
            }

            else -> {
            }

        }

        binding.setWallpaperBtn.setOnClickListener {
            removeTheme()
            startActivityForResult(
                prepareLiveWallpaperIntent(false), SET_WALLPAPER_REQUEST_CODE
            )
        }

        binding.watchAdBtn.setOnClickListener {
            removeTheme()
            interstitialLauncherApply.launch(Intent(this, InterstitialActivity::class.java))
        }

        binding.backButton.setOnClickListener {
            interstitialLauncher.launch(Intent(this, InterstitialActivity::class.java))
            finish()
        }
    }


    private var interstitialLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                kotlin.runCatching {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }


    private var interstitialLauncherApply =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                kotlin.runCatching {
                    startActivityForResult(
                        prepareLiveWallpaperIntent(false), SET_WALLPAPER_REQUEST_CODE
                    )
                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SET_WALLPAPER_REQUEST_CODE) {
            // Check if the wallpaper was successfully set
            if (resultCode == RESULT_OK) {
                showToastMessage("Applied Successfully")

            }
        }
    }

    private fun videoPlay() {

        val rawId = resources.getIdentifier(
            videoPath, "raw", this@SetWallpaperActivity.packageName
        )
        val mVideoView = binding.videoView
        val path = "android.resource://${this@SetWallpaperActivity.packageName}/$rawId"
        val uri = Uri.parse(path)
        mVideoView.setVideoURI(uri)
        mVideoView.requestFocus()
        mVideoView.start()
        mVideoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            // Mute the video by setting the volume to 0 (0.0f)

            mp.setVolume(0.0f, 0.0f)
        }
    }

    override fun onResume() {
        super.onResume()
        videoPlay()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this@SetWallpaperActivity, message, Toast.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun prepareLiveWallpaperIntent(showAllLiveWallpapers: Boolean): Intent {
            val liveWallpaperIntent = Intent()
            if (showAllLiveWallpapers || Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                liveWallpaperIntent.action = WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER
            } else {
                liveWallpaperIntent.action = WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
                val p = LiveWallpaperService::class.java.`package`.name
                val c = LiveWallpaperService::class.java.canonicalName
                liveWallpaperIntent.putExtra(
                    WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(p, c)
                )
            }
            return liveWallpaperIntent
        }
    }

    private fun removeTheme() {
        // Run the function on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myWallpaperManager = WallpaperManager.getInstance(applicationContext)
                myWallpaperManager.clear()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        interstitialLauncher.launch(Intent(this, InterstitialActivity::class.java))
        finish()
    }
}