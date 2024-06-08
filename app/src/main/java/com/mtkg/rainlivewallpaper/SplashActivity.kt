package com.mtkg.rainlivewallpaper

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mtkg.rainlivewallpaper.ads.GdprClass
import com.mtkg.rainlivewallpaper.ads.OpenAppActivity
import com.mtkg.rainlivewallpaper.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        binding.animationView.playAnimation()

        val gdpr = GdprClass(this@SplashActivity)
        gdpr.setGdpr {
            Log.d(gdpr.TAG,"onCreate:  setGdpr  $it")
        }

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity,
                OpenAppActivity::class.java))
            finish()
        }, 3500)
    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.animationView.cancelAnimation()
    }

}