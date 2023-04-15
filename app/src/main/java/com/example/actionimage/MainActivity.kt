package com.example.actionimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.actionimage.ui.theme.ActionImageTheme

data class DropDownItem(
    val icon: Int,
    val text: String,
    val action: () -> Unit
)

data class MyImage(val id: Int, val title : String, @DrawableRes val drawable: Int, val isSelected: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val imagesViewModel by viewModels<ImagesViewModel>()
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
                items(imagesViewModel.images.size) {
                    SelectableImage(
                        image = imagesViewModel.images[it],
                        onClick = { imageIndex ->
                            imagesViewModel.updateState(imageIndex)
                        }
                    )
                }
            }
        }
//            val context = LocalContext.current
//            val images = listOf(
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house),
//                painterResource(id = R.drawable.house)
//            )
//            ImageGallery(
//                painters = images,
//                dropdownItems = listOf(
//                    DropDownItem(
//                        icon = R.drawable.baseline_delete_24,
//                        text = "Kép törlése",
//                        action = {
//                            Toast.makeText(context, "Kép törölve", Toast.LENGTH_SHORT).show()
//                        }),
//                    DropDownItem(
//                        icon = R.drawable.baseline_mode_edit_24,
//                        text = "Beállítás borítóképnek",
//                        action = {
//                            Toast.makeText(context, "Beállítás borítóképnek", Toast.LENGTH_SHORT)
//                                .show()
//                        }),
//                    DropDownItem(
//                        icon = R.drawable.baseline_open_in_full_24,
//                        text = "Megnyitás nagyban",
//                        action = {
//                            Toast.makeText(context, "Megnyitás nagyban", Toast.LENGTH_SHORT).show()
//                        }),
//                )
//            )
//        }

    }


    @Composable
    fun SelectableImage(
        modifier: Modifier = Modifier,
        image: MyImage,
        onClick: (Int) -> Unit
    ) {
        Column(
            modifier = modifier
                .clickable {
                    onClick(image.id)
                }
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = null
                )
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

    @Composable
    fun ImageGallery(painters: List<Painter>, dropdownItems: List<DropDownItem>) {
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
            items(painters.size) {
                ActionImage(
                    painter = painters[it],
                    dropdownItems = dropdownItems
                )
            }
        }
    }

    @Composable
    fun ActionImage(
        modifier: Modifier = Modifier,
        painter: Painter,
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
                    painter = painter,
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

    @Preview
    @Composable
    fun DefaultPreview() {
        ActionImageTheme {
            ActionImage(
                painter = painterResource(id = R.drawable.house),
                dropdownItems = listOf()
            )
        }
    }
}