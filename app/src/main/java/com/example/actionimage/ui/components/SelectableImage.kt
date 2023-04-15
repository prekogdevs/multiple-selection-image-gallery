package com.example.actionimage.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.actionimage.domain.DropDownItem
import com.example.actionimage.domain.GalleryImage

@Composable
fun SelectableImage(
    modifier: Modifier = Modifier,
    image: GalleryImage,
    dropdownItems: List<DropDownItem>,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onClick(image.id)
            }
    ) {
        Box {
            ActionImage(image = image, dropdownItems = dropdownItems)
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = { onClick(image.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selectable Item Icon",
                    tint = if (image.isSelected) Color.Green.copy(alpha = 0.5f) else Color.Transparent
                )
            }
        }
    }
}