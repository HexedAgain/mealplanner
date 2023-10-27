package com.example.lab.ui

import android.graphics.Bitmap
import android.graphics.ColorSpace
import android.graphics.DrawFilter
import android.graphics.ImageDecoder
import android.graphics.ImageDecoder.OnHeaderDecodedListener
import android.media.Image
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.decodeBitmap
import androidx.core.net.toFile
import com.example.core.navigation.Navigator
import com.example.core.ui.ThemedAppBarScreen
import com.example.lab.nav.AddRecipeNavScreen
import com.example.lab.ui.theme.LocalLabScreenTheme
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.net.URI

@Composable
fun LabScreen(
    labViewModel: AddRecipeScreenViewModel = koinViewModel(),
) {
    val theme = LocalLabScreenTheme.current
    ThemedAppBarScreen(
        titleResId = theme.text.labScreenTitle
    ) {
        LazyColumn(
            modifier = theme.modifier.labScreenRoot
        ) {
            item {
                AddRecipeSection(
//                    addRecipeHandler = labViewModel,
                    navigator = labViewModel
                )
            }
            item {
                GetRecipeFromImage()
            }
        }
    }
}

@Composable
fun GetRecipeFromImage() {
    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val recogniser: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
//                val imageBitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val imageSource: ImageDecoder.Source = ImageDecoder.createSource(context.contentResolver, uri)
                val imageBitmap: Bitmap = imageSource.decodeBitmap {x, y ->
                    this.setTargetSize(x.size.width / 2, x.size.height / 2)
                    this.setPostProcessor { it ->
                        it.drawFilter = DrawFilter()
                        1
                    }
                }
//                val imageBitmap: Bitmap = ImageDecoder.decodeBitmap(imageSource)
//                val imageBitmap: Bitmap = ImageDecoder.decodeBitmap(imageSource) { decoder, info, source ->
//                    TODO("Not yet implemented")
//                }
                val image = InputImage.fromBitmap(imageBitmap, 0)
                recogniser.process(image)
                    .addOnSuccessListener {
                        val a = 5
                    }
                    .addOnFailureListener {
                        val a = 5
                    }
            }
        }
    }
    Button(
        onClick = {
            getContent.launch("image/*")
        }
    ) {
        Text(text = "Get recipe from image")
    }
}

@Composable
fun AddRecipeSection(
//    addRecipeHandler: AddRecipeHandler,
    navigator: Navigator
) {
    val theme = LocalLabScreenTheme.current
    Button(
        onClick = {
            navigator.navigate(AddRecipeNavScreen().routeName)
        }
    ) {
        Text(text = stringResource(theme.text.addNewRecipe))
    }
}