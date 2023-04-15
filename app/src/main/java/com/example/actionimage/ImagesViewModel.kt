package com.example.actionimage

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ImagesViewModel : ViewModel() {
    private val imgs = listOf(
        MyImage(0, "Picture Zero", R.drawable.house, false),
        MyImage(1, "Picture One", R.drawable.house, false),
        MyImage(2, "Picture Two", R.drawable.house, false),
        MyImage(3, "Picture Three", R.drawable.house, false),
        MyImage(4, "Picture Four", R.drawable.house, false),
        MyImage(5, "Picture Five", R.drawable.house, false)
    )

    val images = mutableStateListOf<MyImage>()

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