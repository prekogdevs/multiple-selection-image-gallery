package com.example.actionimage.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.actionimage.R
import com.example.actionimage.domain.DropDownItem
import com.example.actionimage.domain.GalleryImage

@Composable
fun ActionImage(
    modifier: Modifier = Modifier,
    image: GalleryImage,
    dropdownItems: List<DropDownItem>
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Card(
        elevation = 4.dp,
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(modifier = modifier) {
            Image(
                modifier = modifier,
                painter = painterResource(id = image.drawable),
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            0F to Color.Black,
                            1F to Color.Transparent,
                        )
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .background(
                            color = Color.DarkGray.copy(alpha = 0.8F),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .pointerInput(true) {
                            detectTapGestures(
                                onPress = {
                                    isContextMenuVisible = true
                                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                }
                            )
                        },
                    tint = Color.Unspecified,
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(onClick = {
                    it.action()
                    isContextMenuVisible = false
                }) {
                    Row {
                        Icon(
                            painter = painterResource(id = it.icon),
                            modifier = Modifier.padding(end = 8.dp),
                            contentDescription = it.text
                        )
                        Text(text = it.text)
                    }
                }
            }
        }
    }
}