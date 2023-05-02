package com.example.wallpaper

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.core.View
import kotlinx.android.synthetic.main.item.view.*

class WallpapersListAdapter(
    var wallpapersList: List<WallpapersModel>,
    private val clickListener: (WallpapersModel) -> Unit,
) :
    RecyclerView.Adapter<WallpapersListAdapter.WallpapersViewHolder>() {
    class WallpapersViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wallpapers: WallpapersModel, clickListener: (WallpapersModel) -> Unit) {
            Glide.with(itemView.context).load(wallpapers.thumbnail).listener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        itemView.prg_item.visibility = android.view.View.GONE
                        return false
                    }
                }

            ).into(itemView.iv_item)
            itemView.setOnClickListener {
                clickListener(wallpapers)
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WallpapersListAdapter.WallpapersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return WallpapersViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WallpapersListAdapter.WallpapersViewHolder,
        position: Int,
    ) {
        (holder as WallpapersViewHolder).bind(wallpapersList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return wallpapersList.size
    }
}