package com.mtkg.rainlivewallpaper

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mtkg.rainlivewallpaper.ads.Banner
import com.mtkg.rainlivewallpaper.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themesAdapter: ThemesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            Banner.show(
                this@MainActivity, binding.bannerAd, this@MainActivity,
            )
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        themesAdapter = ThemesAdapter(this@MainActivity)
        binding.rv.adapter = themesAdapter


    }
}