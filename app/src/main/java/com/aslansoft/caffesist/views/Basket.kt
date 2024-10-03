package com.aslansoft.caffesist.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.classes.CardField
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun BasketScreen(navController: NavHostController, totalPrice: String?) {
    val db = Firebase.firestore
    var cardNo by remember { mutableStateOf("") }
    var cardExp by remember { mutableStateOf(TextFieldValue()) }
    var cvv by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            CardField(cardNo) {
                cardNo = it
            }
            Spacer(Modifier.padding(vertical = 5.dp))
            Row(modifier = Modifier.fillMaxWidth(0.7f),horizontalArrangement = Arrangement.SpaceBetween){
                OutlinedTextField(modifier = Modifier.fillMaxWidth(0.4f),onValueChange = { newValue ->
                    var text = newValue.text
                    var selectionIndex = newValue.selection.end

                    if (text.length <= 5) {
                        if (text.length >= 2 && !text.contains("/")) {
                            val month = text.substring(0, 2)

                            val monthInt = month.toIntOrNull()
                            if (monthInt != null && monthInt in 1..12) {

                                text = "$month/${text.substring(2)}"
                                selectionIndex = 3
                            } else {
                                text = text.dropLast(1)
                                selectionIndex = text.length
                            }
                        }

                        cardExp = TextFieldValue(
                            text = text,
                            selection = TextRange(selectionIndex)
                        )
                    }
                }, value = cardExp,
                    label = {Text("SKT:")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))


                OutlinedTextField(modifier = Modifier.fillMaxWidth(0.8f),value = cvv, onValueChange = {
                    if(cvv.length < 3){
                        cvv = it
                    }
                }, label = { Text("CVV:") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }
            Spacer(Modifier.padding(vertical = 10.dp))
            ElevatedButton(onClick = {}, colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
            )) {
                Text("Ödemeyi\nTamamla")
            }
        }
    }
    BackHandler(true) {
        navController.popBackStack()
    }
}