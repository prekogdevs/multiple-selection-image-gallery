package com.example.actionimage.domain

import androidx.annotation.DrawableRes

data class GalleryImage(val id: Int, val title : String, @DrawableRes val drawable: Int, val isSelected: Boolean)