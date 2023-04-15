package com.example.actionimage.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.actionimage.R
import com.example.actionimage.domain.GalleryImage

class ImagesViewModel : ViewModel() {
    private val imgs = listOf(
        GalleryImage(0, "Picture Zero", R.drawable.house, false),
        GalleryImage(1, "Picture One", R.drawable.house, false),
        GalleryImage(2, "Picture Two", R.drawable.house, false),
        GalleryImage(3, "Picture Three", R.drawable.house, false),
        GalleryImage(4, "Picture Four", R.drawable.house, false),
        GalleryImage(5, "Picture Five", R.drawable.house, false)
    )

    val images = mutableStateListOf<GalleryImage>()

    init {
        images.addAll(imgs)
    }

    fun updateState(id: Int) {
        images[id] = images[id].copy(isSelected = !images[id].isSelected)
        val selectedImages = images.filter { it.isSelected }

        Log.d(
            "Selected images: ",
            "Count: ${selectedImages.size}, " +
                    "names: ${selectedImages.joinToString { it.title + "," }}"
        )
    }
}