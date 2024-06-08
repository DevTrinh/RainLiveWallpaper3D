package com.mtkg.rainlivewallpaper.ads

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mtkg.rainlivewallpaper.Extensions.hideSystemUI
import com.mtkg.rainlivewallpaper.R
import com.mtkg.rainlivewallpaper.databinding.ActivityInterstitialBinding

class InterstitialActivity : AppCompatActivity() {
    lateinit var binding: ActivityInterstitialBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterstitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadLow()
        hideSystemUI()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

//    private fun loadHigh() {
//        kotlin.runCatching {
//            val adRequest = AdRequest.Builder().build()
//            val unitId = getString(R.string.hightInterstitial)
//
//            InterstitialAd.load(
//                this,
//                unitId,
//                adRequest,
//                object : InterstitialAdLoadCallback() {
//                    override fun onAdFailedToLoad(adError: LoadAdError) {
//                        loadMedium()
//                    }
//
//                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                        kotlin.runCatching {
//                            interstitialAd.show(this@InterstitialActivity)
//                            interstitialAd.fullScreenContentCallback =
//                                object : FullScreenContentCallback() {
//                                    override fun onAdDismissedFullScreenContent() {
//                                        super.onAdDismissedFullScreenContent()
//                                        setResult(Activity.RESULT_OK)
//                                        finish()
//                                    }
//
//                                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                                        super.onAdFailedToShowFullScreenContent(p0)
//                                        setResult(Activity.RESULT_OK)
//                                        finish()
//                                    }
//                                }
//                        }.onFailure {
//                            setResult(Activity.RESULT_OK)
//                            finish()
//                        }
//                    }
//                })
//        }.onFailure {
//            setResult(Activity.RESULT_OK)
//            finish()
//        }
//    }
//
//    private fun loadMedium() {
//        kotlin.runCatching {
//            val adRequest = AdRequest.Builder().build()
//            val unitId = getString(R.string.mediumInterstitial)
//            InterstitialAd.load(
//                this,
//                unitId,
//                adRequest,
//                object : InterstitialAdLoadCallback() {
//                    override fun onAdFailedToLoad(adError: LoadAdError) {
//                        loadLow()
//                    }
//
//                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                        kotlin.runCatching {
//                            interstitialAd.show(this@InterstitialActivity)
//                            interstitialAd.fullScreenContentCallback =
//                                object : FullScreenContentCallback() {
//                                    override fun onAdDismissedFullScreenContent() {
//                                        super.onAdDismissedFullScreenContent()
//                                        setResult(Activity.RESULT_OK)
//                                        finish()
//                                    }
//
//                                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                                        super.onAdFailedToShowFullScreenContent(p0)
//                                        setResult(Activity.RESULT_OK)
//                                        finish()
//                                    }
//                                }
//                        }.onFailure {
//                            setResult(Activity.RESULT_OK)
//                            finish()
//                        }
//                    }
//                })
//        }.onFailure {
//            setResult(Activity.RESULT_OK)
//            finish()
//        }
//    }
    private fun loadLow() {
        kotlin.runCatching {
            val adRequest = AdRequest.Builder().build()
            val unitId = getString(R.string.interstitialID)
            InterstitialAd.load(
                this,
                unitId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        kotlin.runCatching {
                            interstitialAd.show(this@InterstitialActivity)
                            interstitialAd.fullScreenContentCallback =
                                object : FullScreenContentCallback() {
                                    override fun onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent()
                                        setResult(Activity.RESULT_OK)
                                        finish()
                                    }

                                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                        super.onAdFailedToShowFullScreenContent(p0)
                                        setResult(Activity.RESULT_OK)
                                        finish()
                                    }
                                }
                        }.onFailure {
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }
                })
        }.onFailure {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
}