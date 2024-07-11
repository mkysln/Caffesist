package com.aslansoft.caffesist.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.ui.theme.Pink80
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Login(navController: NavHostController) {
    val auth = Firebase.auth
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(modifier = Modifier.size(150.dp),bitmap = ImageBitmap.imageResource(id = R.drawable.caffesist_logo_bg), contentDescription = "")
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OutlinedTextField(value = email.value , onValueChange = {
                email.value = it
            },
                label = {
                    Text(text = "Email:")
                },
                singleLine = true,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            OutlinedTextField(value = password.value , onValueChange = {
                password.value = it
            }, label = {
                Text(text = "Parola:")
            },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                 colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            ElevatedButton(onClick = { navController.navigate("main_screen")},
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )) {
                Row {
                    Text(text = "Giriş Yap")
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            Text(text = "Hesabın Yok Mu?", color = Color.White)
            TextButton(onClick = { navController.navigate("register_screen") }) {
                Text(text = "Kayıt Ol", color = Pink80,style = TextStyle(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary,
                        offset = Offset(5.0f,10.0f),
                        blurRadius = 3f
                    )
                ) )
            }
        }
    }
    val actvity = (LocalContext.current as? Activity)
    BackHandler(true) {
        actvity?.finish()
    }
}