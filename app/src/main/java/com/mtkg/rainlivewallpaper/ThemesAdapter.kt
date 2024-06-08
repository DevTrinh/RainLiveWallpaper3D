package com.mtkg.rainlivewallpaper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ThemesAdapter(private val context: Context) :
    RecyclerView.Adapter<ThemesAdapter.ViewHolder>() {

    private var images = intArrayOf(
        R.drawable.img_3, R.drawable.img_4,
        R.drawable.img_11,
                R.drawable.img_5, R.drawable.img_6,
        R.drawable.img_7, R.drawable.img_8,
        R.drawable.img_1, R.drawable.img_2,
        R.drawable.img_9, R.drawable.img_10
    )

    private var vediolist = arrayOf(
        "vdo_3", "vdo_4",
        "vdo_11",
        "vdo_5", "vdo_6",
        "vdo_7", "vdo_8",
        "vdo_1", "vdo_2",
        "vdo_9", "vdo_10"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.imageView.setImageResource(images[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SetWallpaperActivity::class.java)
            intent.putExtra("firstVideo", vediolist[position])
            intent.putExtra("position", position)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_theme)
    }
}
