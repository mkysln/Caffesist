package com.aslansoft.caffesist.views


import android.util.Log
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.classes.BinInfo
import com.aslansoft.caffesist.classes.CardField
import com.aslansoft.caffesist.classes.loadBinData
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
    val result = remember { mutableStateOf<BinInfo?>(null) }
    val binMap = remember { mutableStateOf(loadBinData(context)) }
    LaunchedEffect(cardNo.length) {
        result.value = binMap.value[cardNo.take(6)]
    }
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
                    .width(360.dp)
                    .height(200.dp)
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
                            .padding(start = 20.dp, end = 4.dp),
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
                        result.value = binMap.value[cardNo]
                        var model = R.drawable.remove
                        var cardservice = ""

                        if (cardservice == "mastercard"){
                                 model = R.drawable.mastercard
                        }else if (cardservice == "visa"){
                            model = R.drawable.visa
                        }else if (cardservice == "troy"){
                           model = R.drawable.troy
                        }else{
                            model = R.drawable.remove
                        }
                        val painter = rememberAsyncImagePainter(model = model)
                        Image(modifier = Modifier.size(50.dp), painter = painter, contentDescription = null)
                    }
                }
            }
            Column(Modifier.border(1.dp,Color.Yellow)) {
                result.value.let {
                    Column {
                        Text(cardNo, color = Color.White)
                        Text("Marka: ${it?.bin}",color = Color.White)
                        Text("Tür: ${it?.bankCode}",color = Color.White)
                        Text("Ülke: ${it?.bankName}",color = Color.White)
                        Text("Bankanın Web Sitesi: ${it?.type ?: "Yok"}",color = Color.White)
                        Text("Telefon: ${it?.subType ?: "Yok"}",color = Color.White)
                    }

                }
            }

        }
    }
    BackHandler(true) {
        navController.popBackStack()
    }
}
