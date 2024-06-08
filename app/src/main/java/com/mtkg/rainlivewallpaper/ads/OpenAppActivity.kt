package com.mtkg.rainlivewallpaper.ads

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.mtkg.rainlivewallpaper.Extensions.hideSystemUI
import com.mtkg.rainlivewallpaper.R
import com.mtkg.rainlivewallpaper.StartingActivity
import com.mtkg.rainlivewallpaper.databinding.ActivityAppOpenBinding

class OpenAppActivity : AppCompatActivity() {

    lateinit var binding: ActivityAppOpenBinding
    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private val debugTag = "AppOpenManager"
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        binding = ActivityAppOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAd()
    }

    private fun fetchAd() {
        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                moveForward()
            }

            override fun onAdLoaded(p0: AppOpenAd) {
                super.onAdLoaded(p0)
                appOpenAd = p0
                showAdAvailable()
            }
        }
        val request = adRequest
        val unitId = getString(R.string.appOpenID)
        AppOpenAd.load(
            this,
            unitId,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            loadCallback as AppOpenAd.AppOpenAdLoadCallback
        )
    }

    private fun showAdAvailable() {
        val fullScreenContentCallback: FullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    moveForward()
                    Log.d(
                        debugTag, "onAdDismissedFullScreenContent"
                    )
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(
                        debugTag, "onAdFailedToShowFullScreenContent ${adError.cause}"
                    )
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(debugTag, "onAdShowedFullScreenContent")
                }
            }
        appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
        appOpenAd?.show(this)
    }

    private fun moveForward() {
        val mainIntent = Intent(this@OpenAppActivity, StartingActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}