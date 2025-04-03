package com.aslansoft.caffesist.views


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.classes.CardField
import com.aslansoft.caffesist.ui.theme.Pink40
import com.aslansoft.caffesist.ui.theme.Pink80
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun BasketScreen(navController: NavHostController, totalPrice: String?, orderId: String?) {
    val db = Firebase.firestore
    var cardNo by remember { mutableStateOf("") }
    var cardExp by remember { mutableStateOf(TextFieldValue()) }
    var cvv by remember { mutableStateOf("") }
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(102, 0, 51)
                ), modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(150.dp)
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CardField(modifier = Modifier.fillMaxWidth(0.6f), cardNumber = cardNo) {
                                cardNo = it
                            }

                            TextField(value = cvv, onValueChange = {
                                if(cvv.length < 3){
                                    cvv = it
                                }
                            }, placeholder = { Text("cvv") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.colors(
                                    errorContainerColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedPlaceholderColor = Color.White,
                                    focusedPlaceholderColor = Color.White,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedTextColor = Color.White,
                                    selectionColors = TextSelectionColors(
                                        handleColor = Pink80,
                                        backgroundColor = Pink40
                                    ),
                                    cursorColor = Color.White
                                ))
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = 20.dp,end=4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        TextField(
                            modifier = Modifier.fillMaxWidth(0.4f),
                            onValueChange = { newValue ->
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
                                        text = text, selection = TextRange(selectionIndex)
                                    )
                                }
                            },
                            value = cardExp,
                            placeholder = { Text("SKT:") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                errorContainerColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedPlaceholderColor = Color.White,
                                focusedPlaceholderColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedTextColor = Color.White,
                                selectionColors = TextSelectionColors(
                                    handleColor = Pink80,
                                    backgroundColor = Pink40
                                ),
                                cursorColor = Color.White
                            )

                        )
                        Image(modifier = Modifier.size(50.dp),painter = painterResource(R.drawable.card), contentDescription = null)
                    }
                }
            }

        }
    }
    BackHandler(true) {
        navController.popBackStack()
    }
}


//Expiration Date
/*
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
 */


//CVV
/*

 */

/*
ödeme butonu
            ElevatedButton(onClick = {
                db.collection("orders").document(orderId!!).update("payTo",true).addOnSuccessListener {
                    Toast.makeText(context,"Ödeme Başarılı",Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }.addOnFailureListener {e ->
                    Log.e("PayTo","Ödeme Hatası!!",e)
                }
            }, colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
            )) {
                Text("Ödemeyi\nTamamla")
            }
 */