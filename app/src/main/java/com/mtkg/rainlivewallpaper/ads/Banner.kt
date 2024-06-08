package com.mtkg.rainlivewallpaper.ads

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mtkg.rainlivewallpaper.R

object Banner {
    fun show(context: Context, layout: FrameLayout, activity: Activity) {
        kotlin.runCatching {
            if (!activity.isFinishing) {
                MobileAds.initialize(context) {}
                val adView = AdView(context)
                adView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                adView.setAdSize(AdSize(AdSize.FULL_WIDTH, 60))
                adView.adUnitId = context.getString(R.string.NewbannerID)

                val adRequest = AdRequest.Builder().build()
                activity.runOnUiThread {
                    layout.addView(adView)
                        adView.loadAd(adRequest)
                    }
                }
            }
    }
}