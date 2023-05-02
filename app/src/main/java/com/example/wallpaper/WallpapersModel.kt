package com.example.wallpaper

import com.google.firebase.Timestamp
import java.util.*

data class WallpapersModel(
    val name: String = "",
    val image: String = "",
    val thumbnail: String = "",
    val date: Timestamp? = null,
)
