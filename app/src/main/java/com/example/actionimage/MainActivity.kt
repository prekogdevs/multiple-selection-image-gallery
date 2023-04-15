package com.example.actionimage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.example.actionimage.domain.DropDownItem
import com.example.actionimage.ui.components.ImageGallery
import com.example.actionimage.viewmodel.ImagesViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val imagesViewModel by viewModels<ImagesViewModel>()
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
                            Toast.makeText(context, "Beállítás borítóképnek", Toast.LENGTH_SHORT)
                                .show()
                        }),
                    DropDownItem(
                        icon = R.drawable.baseline_open_in_full_24,
                        text = "Megnyitás nagyban",
                        action = {
                            Toast.makeText(context, "Megnyitás nagyban", Toast.LENGTH_SHORT).show()
                        }),
                ),
                onSelectImage = {
                    imagesViewModel.updateState(it)
                })
        }
    }

}


