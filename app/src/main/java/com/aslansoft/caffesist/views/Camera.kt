package com.aslansoft.caffesist.views

import android.annotation.SuppressLint
import android.graphics.Camera
import android.widget.Toast
import androidx.camera.core.CameraControl
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.classes.QRCodeViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Camera(navController: NavHostController) {

    val qrCodeViewModel: QRCodeViewModel = viewModel()
    val qrResult by qrCodeViewModel.qrResult
    val detectButtonText = remember{
        mutableStateOf("Taranıyor...")
    }
    val document = remember {
        mutableStateOf("")
    }
    val collection = remember {
        mutableStateOf("")
    }
    val flashEnable = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var cameraControl: CameraControl? = remember { null }

    // LaunchedEffect qrResult değiştiğinde tüm ekranı sıfırlamak için
        if (qrResult.isNotEmpty()){
            try {
                detectButtonText.value = "Tarama Başarılı"
                val jsonObject = Json.parseToJsonElement(qrResult).jsonObject
                collection.value = jsonObject["colRef"]?.jsonPrimitive?.content ?: ""
                document.value = jsonObject["DocRef"]?.jsonPrimitive?.content ?: ""
                if (collection.value.isNotEmpty() && document.value.isNotEmpty()){
                    detectButtonText.value = "Tarama Başarılı"
                    navController.navigate("menu_screen/${document.value}")
                }else{
                    throw  Exception("Invalid QR CODE")
                }





            } catch (e: Exception) {
                Toast.makeText(context, "Geçersiz QR Kod", Toast.LENGTH_SHORT).show()
                detectButtonText.value = "Taranıyor..."
                collection.value = ""
                document.value = ""
            }
        }


    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener{_,destination,_ ->
            if (destination.route == "menu_screen/{documentId}"){
                document.value = ""
                collection.value = ""
                detectButtonText.value = "Taranıyor..."
                qrCodeViewModel.resetQRResult()
            }

        }

    }
    println(qrResult)
    Surface(modifier = Modifier.fillMaxSize()) {



            CameraPreview(onCameraControl = { control -> cameraControl = control })


        Column(modifier = Modifier
            .fillMaxSize()
            , verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
            IconButton(onClick = {
                if (flashEnable.value){
                    flashEnable.value = false
                    cameraControl?.enableTorch(flashEnable.value)

                }else{
                    flashEnable.value = true
                    cameraControl?.enableTorch(flashEnable.value)

                }
            }) {
                if (flashEnable.value){
                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.flash_off), contentDescription = "flash off", tint = Color.White)

                }else{
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.flash_on), contentDescription = "flash on", tint = Color.White)
                }
            }
            Column (modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                if(collection.value.isNotEmpty()){
                    Box(modifier = Modifier
                        .size(300.dp)
                        .border(width = 1.dp, color = Color.Green, shape = RoundedCornerShape(5.dp)))
                }

                else{
                    Box(modifier = Modifier
                        .size(300.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Yellow,
                            shape = RoundedCornerShape(5.dp)
                        ))

                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }

            }

    }
}

