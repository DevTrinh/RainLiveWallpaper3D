package com.mtkg.rainlivewallpaper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mtkg.rainlivewallpaper.ads.InterstitialActivity
import com.mtkg.rainlivewallpaper.databinding.ActivityStartingBinding

class StartingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CoroutineScope(Dispatchers.IO).launch {
//            NativeAd.showNativeAdvancedAd(
//                this@StartingActivity, this@StartingActivity,binding.nativeAd, NativeType.WITH_MEDIA
//            )
//        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.start.setOnClickListener {
            interstitialLauncher.launch(Intent(this, InterstitialActivity::class.java))
        }
        binding.rateus.setOnClickListener {
            rateApp()
        }
        binding.more.setOnClickListener {
            moreApps()
        }
        binding.share.setOnClickListener {
            shareApp()
        }
    }

    private fun rateApp() {
        val appPackageName = packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    private fun moreApps() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=MTKG+Hub")
                )
            )
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=MTKG+Hub")
                )
            )
        }
    }

    private fun shareApp() {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
        share.putExtra(
            Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=$packageName"
        )
        startActivity(Intent.createChooser(share, "Share link!"))
    }


    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@StartingActivity)
                builder.setTitle(R.string.app_name)
                builder.setMessage("Are you sure you want to exit?")

                builder.setPositiveButton("Yes") { _, _ ->
                    finish()
                }

                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }


    private var interstitialLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                kotlin.runCatching {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }

}