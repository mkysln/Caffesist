package com.aslansoft.caffesist.views

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.ui.theme.Purple40
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
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.login_bg),
                    contentScale = ContentScale.Crop
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f), contentAlignment = Alignment.TopCenter){
                Image(
                    modifier = Modifier.size(200.dp).fillMaxWidth(),
                    bitmap = ImageBitmap.imageResource(id = R.drawable.caffesist_logo),
                    contentDescription = ""
                )
            }


            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            TextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .height(50.dp),
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                label = {
                    Text(text = "Email:")
                },
                singleLine = true,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black, backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            TextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(9.dp))
                    .height(50.dp),
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = {
                    Text(text = "Parola:")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black, backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            ElevatedButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(9.dp),
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        if (email.value.endsWith(".com") && email.value.contains("@")) {
                            if (password.value.length >= 6) {
                                auth.signInWithEmailAndPassword(email.value, password.value)
                                    .addOnSuccessListener {
                                        navController.navigate("main_screen")
                                    }.addOnFailureListener {
                                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            } else {
                                Toast.makeText(context, "Parolanız Hatalı", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Lütfen E-Posta Adresinizi Doğru Biçimde Yazınız",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(context, "Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT)
                            .show()
                    }


                    /*navController.navigate("main_screen")*/
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Purple40, contentColor = Color.White
                )
            ) {
                Text(text = "Giriş Yap", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Text(text = "Hesabın Yok Mu?", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.padding(vertical = 5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Text(modifier = Modifier.clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    navController.navigate(
                        "register_screen"
                    )
                },
                    text = "Kayıt Ol",
                    color = Color(248, 213, 100, 233),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }

        }
    }
    val actvity = (LocalContext.current as? Activity)
    BackHandler(true) {
        actvity?.finish()
    }
}