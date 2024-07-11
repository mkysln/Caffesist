package com.aslansoft.caffesist.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    val userName = remember { mutableStateOf("") }
    val userSurname = remember { mutableStateOf("") }
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
    Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
          OutlinedTextField(value = userName.value, onValueChange = {
              userName.value = it
          }, label = {
              Text(text = "İsim")
          }, colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = Color.White,
              unfocusedTextColor = Color.LightGray,
              unfocusedLabelColor = Color.LightGray,
              unfocusedBorderColor = Color.LightGray
          ),
              singleLine = true)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OutlinedTextField(value = userSurname.value, onValueChange = {
              userSurname.value = it
          }, label = {
              Text(text = "Soy İsim")
            }, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            ),
                singleLine = true)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            OutlinedTextField(value = userEmail.value, onValueChange = {
              userEmail.value = it
          },
                label = {
                    Text(text = "E-posta")
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.MailOutline, contentDescription = "" )
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))

             PhoneField(phone = userPhone.value, mask = "+90 (000) 000 00 00", maskNumber = '0', onPhoneChanged = {
                 userPhone.value = it
             })
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                OutlinedTextField(value = userBirthDate.value, onValueChange ={
                    userBirthDate.value = it
                },
                    readOnly = true,
                    trailingIcon = {
                        Icon(modifier = Modifier.clickable { openDialog.value = true },imageVector = Icons.Default.DateRange, contentDescription = null)
                    }, label = {
                        Text(text = "Doğum Tarihi")
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    singleLine = true)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                OutlinedTextField(value = userPassword.value, onValueChange = {
                    userPassword.value = it
                },
                    label = {
                        Text(text = "Parola")
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                    )

            if (openDialog.value) {
                DatePickerDialog(onDismissRequest = {
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
                        if (selectedMills != null){
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis = selectedMills
                            }
                            userBirthDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${calendar.get(Calendar.YEAR)}"
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
                    DatePicker(state = datePickerState, colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background
                    ), title = { Text(modifier = Modifier.padding(start = 15.dp,top = 5.dp),text = "Doğum Tarihi")})

                }
            }
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            ElevatedButton(onClick = {
                if(userName.value.isNotEmpty() && userSurname.value.isNotEmpty() && userEmail.value.isNotEmpty() && userPhone.value.isNotEmpty() && userPassword.value.isNotEmpty() && userBirthDate.value.isNotEmpty()){
                    if (userEmail.value.endsWith(".com")&&userEmail.value.contains("@")){
                        Toast.makeText(context,"Başarılı",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"Lütfen E-Postanızı Doğru Şekilde Yazınız",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context,"Lütfen Tüm Alanları Doldurun",Toast.LENGTH_SHORT).show()
                }
            }, colors = ButtonDefaults.elevatedButtonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            )) {
                Text(text = "Kaydol")
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "")
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
    OutlinedTextField(
        value = phone,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        label = {
            Text(text = "Telefon No:")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        trailingIcon = { Icon(imageVector = Icons.Outlined.Call, contentDescription = "")},
        singleLine = true
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
