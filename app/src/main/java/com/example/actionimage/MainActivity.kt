package com.example.actionimage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.actionimage.domain.DropDownItem
import com.example.actionimage.ui.components.ImageGallery
import com.example.actionimage.viewmodel.ImagesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val imagesViewModel by viewModels<ImagesViewModel>()
            val sheetState = rememberBottomSheetState(
                initialValue = BottomSheetValue.Collapsed
            )
            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = sheetState
            )
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = imagesViewModel.selectedImagesSize() > 0) {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetBackgroundColor = Color.Gray,
                sheetPeekHeight = (-100).dp,
                sheetContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Kiválaszottál ${imagesViewModel.selectedImagesSize()} képet, szeretnéd törölni őket?",
                            color = Color.White
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "Igen",
                                    color = Color.White
                                )
                            }
                            Button(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "Nem",
                                    color = Color.White
                                )
                            }
                        }

                    }
                }) {
                ImageGallery(
                    images = imagesViewModel.images,
                    dropdownItems = listOf(
                        DropDownItem(
                            icon = R.drawable.baseline_delete_24,
                            text = "Kép törlése",
                            action = {
                                Toast.makeText(context, "Kép törölve", Toast.LENGTH_SHORT).show()
                            }),
                        DropDownItem(
                            icon = R.drawable.baseline_mode_edit_24,
                            text = "Beállítás borítóképnek",
                            action = {
                                Toast.makeText(
                                    context,
                                    "Beállítás borítóképnek",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }),
                        DropDownItem(
                            icon = R.drawable.baseline_open_in_full_24,
                            text = "Megnyitás nagyban",
                            action = {
                                Toast.makeText(context, "Megnyitás nagyban", Toast.LENGTH_SHORT)
                                    .show()
                            }),
                    ),
                    onSelectImage = {
                        imagesViewModel.updateState(it)
                    })
            }
        }
    }

}


