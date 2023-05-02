package com.example.wallpaper.Fragment

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.loader.content.AsyncTaskLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaper.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {

    private var image: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = DetailFragmentArgs.fromBundle(requireArguments()).wallpaperImage


        btn_detail.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_detail -> setWallpaper()

        }
    }

    private fun setWallpaper() {

        btn_detail.isEnabled = false
        btn_detail.text = "Wallpaper Set"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btn_detail.setTextColor(resources.getColor(R.color.red,null))
        }
        val bitmap: Bitmap = iv_detail.drawable.toBitmap()
        val task: SetWallpaperTask = SetWallpaperTask(requireContext(), bitmap)
        task.execute(true)

    }

    companion object {
        class SetWallpaperTask internal constructor(
            private val context: Context,
            private val bitmap: Bitmap,
        ) :
            AsyncTask<Boolean, String, String>() {
            override fun doInBackground(vararg p0: Boolean?): String {
                val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
                return "Wallpaper Set"
            }

        }
    }

    override fun onStart() {
        super.onStart()
    if (image!=null){
        Glide.with(requireContext()).load(image).listener(
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

                    btn_detail.visibility = View.VISIBLE

                    prg_detail.visibility = View.INVISIBLE
                    return false
                }

            }
        ).into(iv_detail)
    }
    }
    override fun onStop() {
        super.onStop()
        Glide.with(requireContext()).clear(iv_detail)
    }
}