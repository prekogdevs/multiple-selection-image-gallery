package com.example.actionimage.domain

data class DropDownItem(
    val icon: Int,
    val text: String,
    val action: () -> Unit
)