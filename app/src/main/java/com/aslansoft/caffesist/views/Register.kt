@file:Suppress("DEPRECATION")

package com.aslansoft.caffesist.views

import EyeClosed
import EyeOpen
import android.util.Log
import android.view.animation.Transformation
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.ImagePainter
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.ui.theme.Purple40
import com.aslansoft.caffesist.ui.theme.Purple80
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    val userName = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember {
        mutableStateOf("")
    }
    val userPhone = remember {
        mutableStateOf("")
    }
    val userBirthDate = remember {
        mutableStateOf("")
    }
    val openDialog = remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val auth = Firebase.auth
    val db = Firebase.firestore
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(R.drawable.background), contentScale = ContentScale.Crop),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.padding(vertical = 40.dp))
            Image(
                modifier = Modifier.size(130.dp),
                bitmap = ImageBitmap.imageResource(R.drawable.caffesist_logo_bg),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            TextField(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(9.dp)
                    ),
                value = userName.value,
                onValueChange = {
                    userName.value = it

                },
                singleLine = true,
                label = {
                    Text("Ad Soyad:")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black,
                        backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            //E-posta
            TextField(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(9.dp)
                    ),
                value = userEmail.value,
                onValueChange = {
                    userEmail.value = it

                },
                singleLine = true,
                label = { Text("E-Posta:") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black,
                        backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ), trailingIcon = {
                    Icon(imageVector = Icons.Default.MailOutline, contentDescription = "")
                }
            )


            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            PhoneField(
                phone = userPhone.value,
                mask = "+90 (000) 000 00 00",
                maskNumber = '0',
                onPhoneChanged = {
                    userPhone.value = it
                })
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            TextField(
                modifier = Modifier.clip(RoundedCornerShape(9.dp)),
                value = userBirthDate.value,
                onValueChange = {
                    userBirthDate.value = it
                },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { openDialog.value = true },
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Doğum Tarihi")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black,
                        backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            val hidePassword = remember { mutableStateOf(true) }
            TextField(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(9.dp)
                    ),
                value = userPassword.value,
                onValueChange = {
                    userPassword.value = it

                },
                singleLine = true,
                label = { Text("Parola") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Black,
                        backgroundColor = Color.Gray
                    ),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedContainerColor = Color(0x80FFFAFA),
                    unfocusedContainerColor = Color(0x80FFFAFA),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                visualTransformation = if (hidePassword.value) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    if (hidePassword.value) {
                        IconButton(onClick = { hidePassword.value = false }) {
                            Icon(imageVector = EyeClosed, contentDescription = "")

                        }
                    } else {
                        IconButton(onClick = { hidePassword.value = true }) {
                            Icon(imageVector = EyeOpen, contentDescription = "")
                        }
                    }
                }
            )

            if (openDialog.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        dayContentColor = Color.White,
                        weekdayContentColor = MaterialTheme.colorScheme.primary,
                        subheadContentColor = MaterialTheme.colorScheme.primary
                    ), confirmButton = {
                        Button(onClick = {
                            val selectedMills = datePickerState.selectedDateMillis
                            if (selectedMills != null) {
                                val calendar = Calendar.getInstance().apply {
                                    timeInMillis = selectedMills
                                }
                                userBirthDate.value =
                                    "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
                                        calendar.get(Calendar.YEAR)
                                    }"
                                val timestamp = Timestamp(calendar.time)
                                println(timestamp)
                            }
                            openDialog.value = false
                        }) {
                            Text(text = "Onayla")
                        }
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = true)
                ) {
                    DatePicker(state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        title = {
                            Text(
                                modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                                text = "Doğum Tarihi"
                            )
                        })

                }
            }
            Spacer(modifier = Modifier.padding(vertical = 35.dp))
            ElevatedButton(
                modifier = Modifier
                    .width(280.dp)
                    .height(58.dp),
                onClick = {
                    val currentDate = Calendar.getInstance()
                    val birthDate = Calendar.getInstance().apply {
                        val parts = userBirthDate.value.split(".")
                        set(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
                    }
                    var age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
                    if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                        age--
                    }
                    if (userName.value.isNotEmpty() && userEmail.value.isNotEmpty() && userPhone.value.isNotEmpty() && userPassword.value.isNotEmpty() && userBirthDate.value.isNotEmpty()) {
                        if (userEmail.value.endsWith(".com") && userEmail.value.contains("@")) {
                            if (userPhone.value.startsWith("0")) {
                                if (userPassword.value.length >= 6) {
                                    if (age >= 18) {
                                        val data = hashMapOf(
                                            "name" to userName.value,
                                            "email" to userEmail.value,
                                            "phone" to userPhone.value,
                                            "birthDate" to userBirthDate.value
                                        )
                                        auth.fetchSignInMethodsForEmail(userEmail.value)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    auth.createUserWithEmailAndPassword(
                                                        userEmail.value,
                                                        userPassword.value
                                                    ).addOnCompleteListener { createUser ->
                                                        if (createUser.isSuccessful) {
                                                            val currentUser = auth.currentUser
                                                            currentUser?.sendEmailVerification()
                                                                ?.addOnCompleteListener { verified ->
                                                                    if (verified.isSuccessful) {
                                                                        Toast.makeText(
                                                                            context,
                                                                            "Kaydınızı Tamamlamak İçin Lütfen E-Postanızı Doğrulayınız",
                                                                            Toast.LENGTH_LONG
                                                                        ).show()
                                                                        db.collection("users")
                                                                            .document(userEmail.value)
                                                                            .set(data)
                                                                            .addOnSuccessListener {
                                                                                println("person data succes database writing")
                                                                            }
                                                                        navController.navigate("login_screen")
                                                                    } else {
                                                                        Toast.makeText(
                                                                            context,
                                                                            "E-Posta Gönderilirken Bir Hata Oluştu",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                        println("sendEmailVerification:failure" + verified.exception)
                                                                    }
                                                                }
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.w(
                                                                "ErrorAuth",
                                                                "signInWithEmail:failure",
                                                                task.exception
                                                            )
                                                            Toast.makeText(
                                                                context,
                                                                task.exception.toString(),
                                                                Toast.LENGTH_SHORT,
                                                            ).show()

                                                        }

                                                    }
                                                }

                                            }

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Kullanıcı 18 Yaşın Altında Olamaz",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Parola En Az 6 Karakterden Oluşmalıdır",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Lütfen Telefon Numaranızı Doğru Biçimde Yazınız",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Lütfen E-Postanızı Doğru Biçimde Yazınız",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(context, "Lütfen Tüm Alanları Doldurun", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = Color.White,
                    containerColor = Purple40
                ),
                shape = RoundedCornerShape(9.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp,
                    focusedElevation = 30.dp,
                    hoveredElevation = 35.dp
                )
            ) {
                Text(text = "Kaydol", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun PhoneField(
    phone: String,
    modifier: Modifier = Modifier,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
    onPhoneChanged: (String) -> Unit
) {
    TextField(
        modifier = Modifier.clip(shape = RoundedCornerShape(9.dp)),
        value = phone,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        label = {
            Text(text = "Telefon No:")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        trailingIcon = { Icon(imageVector = Icons.Outlined.Call, contentDescription = "") },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            selectionColors = TextSelectionColors(
                handleColor = Color.Black,
                backgroundColor = Color.Gray
            ),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedContainerColor = Color(0x80FFFAFA),
            unfocusedContainerColor = Color(0x80FFFAFA),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}
