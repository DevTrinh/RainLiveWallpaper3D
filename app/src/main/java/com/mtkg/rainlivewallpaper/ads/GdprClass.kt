package com.mtkg.rainlivewallpaper.ads

import android.app.Activity
import android.util.Log
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.mtkg.rainlivewallpaper.BuildConfig
import java.util.concurrent.atomic.AtomicBoolean

class GdprClass(var activity: Activity) {
    val TAG = "MyGdpr"
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    fun setGdpr(initSdk:(Boolean)->Unit) {

        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("ee57123e77eeed3b20a8f0b1e4efb6a10c7a8224")
            .build()

        val params = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
//            .setConsentDebugSettings(debugSettings)
            .build()
        val consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    activity
                ) { loadAndShowError: FormError? ->
                    if (loadAndShowError != null) {
                        // Consent gathering failed.
                        initSdk(false)
                        Log.w(
                            TAG, String.format(
                                "loadAndShowError  %s: %s",
                                loadAndShowError.errorCode,
                                loadAndShowError.message
                            )
                        )
                    }
                }
            },
            { requestConsentError: FormError ->
                // Consent gathering failed.
                Log.w(
                    TAG, String.format(
                        "requestConsentError  %s: %s",
                        requestConsentError.errorCode,
                        requestConsentError.message
                    )
                )
            })

//       FOR Testing purposes only. You shouldn't call reset() in production code.
        if (BuildConfig.DEBUG) {
            consentInformation.reset()
        }
        if (consentInformation.canRequestAds()) {
            if (isMobileAdsInitializeCalled.getAndSet(true)) {
                initSdk(false)
                return
            }
         initSdk(true)
        }
    }



}