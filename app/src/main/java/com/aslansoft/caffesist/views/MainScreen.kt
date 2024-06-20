package com.aslansoft.caffesist.views

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current

    val cameraGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        if (isGranted){
            cameraGranted.value = true
            navController.navigate("Camera")
        }else{
            Toast.makeText(context,
                "Kamera İzni Vermeniz Gerekiyor",
                Toast.LENGTH_SHORT).show()
        }

    }
    Surface(color = MaterialTheme.colorScheme.background) {

        Row (
            Modifier
                .fillMaxWidth()
                .height(100.dp)
               , horizontalArrangement = Arrangement.Center){
            Text(text = "CaffeSist'e Hoşgeldiniz", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Button(onClick = {  if (!cameraGranted.value){
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }else{
                navController.navigate("Camera")
            } }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )) {
                Text(text = "Qr Tara")
            }
        }

    }



}