package com.example.wallpaper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class WallpapersViewModel : ViewModel() {

    private val firebaseRepository: FirebaseRepository = FirebaseRepository()

    private val wallpaperList: MutableLiveData<List<WallpapersModel>> by lazy {
        MutableLiveData<List<WallpapersModel>>().also {
            loadWallpapersData()
        }
    }

    fun getWallpapersList(): LiveData<List<WallpapersModel>> {
        return wallpaperList


    }

    fun loadWallpapersData() {

        firebaseRepository.queryWallpapers().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (result!!.isEmpty) {

                } else {
                    if (wallpaperList.value == null) {
                        wallpaperList.value = result.toObjects(WallpapersModel::class.java)

                    } else {

                        wallpaperList.value = (result.toObjects(WallpapersModel::class.java))


                    }
                    val lastItem: DocumentSnapshot = result.documents[result.size() - 1]
                    firebaseRepository.lastVisible = lastItem
                }

            } else {
                Log.d("VIEW_MODEL_LOG", "Error:${it.exception!!.message}")
            }

        }
    }
}