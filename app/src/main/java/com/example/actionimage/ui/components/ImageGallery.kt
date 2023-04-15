package com.example.actionimage.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.actionimage.domain.DropDownItem
import com.example.actionimage.domain.GalleryImage

@Composable
fun ImageGallery(
    images: List<GalleryImage>,
    dropdownItems: List<DropDownItem>,
    onSelectImage: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(images.size) { it ->
            SelectableImage(
                image = images[it],
                onClick = {
                    onSelectImage(it)
                },
                dropdownItems = dropdownItems
            )
        }
    }
}