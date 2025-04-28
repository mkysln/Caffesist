package com.aslansoft.caffesist.views


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.aslansoft.caffesist.classes.ApiResponse
import com.aslansoft.caffesist.classes.CardField
import com.aslansoft.caffesist.classes.RetrofitClient
import com.aslansoft.caffesist.ui.theme.Pink40
import com.aslansoft.caffesist.ui.theme.Pink80
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun BasketScreen(navController: NavHostController, totalPrice: String?, orderId: String?) {
    var cardNo by remember { mutableStateOf("") }
    var cardExp by remember { mutableStateOf(TextFieldValue()) }
    var cvv by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var cardScheme by remember { mutableStateOf<String?>(null) }
    var bankName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(cardNo) {
        if (cardNo.length == 6) {
            isLoading = true


            suspend fun tryBinRequest() {
                delay(500) // kullanıcı yazmayı bitirmiş olsun

                RetrofitClient.binTableApi.getBinInfo(cardNo.take(6), "14dd1bd4fcc9a3fc8dd209999efbb1514db735c4").enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            isLoading = false
                            cardScheme = response.body()?.data?.card?.scheme
                            bankName = response.body()?.data?.bank?.name
                            Log.d("BasketScreen", "Card Scheme: $cardScheme, Bank: $bankName")
                        } else {
                            isLoading = false
                            Log.e("BasketScreen", "API Hata: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("BasketScreen", "İstek başarısız: ${t.message}")
                    }
                })
            }

            tryBinRequest()
        }
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

                            TextField(
                                value = cvv, onValueChange = {
                                if (it.length < 4 && it.all { char -> char.isDigit() }) {
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
                                )
                            )
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

                        val model = when (cardScheme?.lowercase()) {
                            "mastercard" -> R.drawable.mastercard
                            "visa" -> R.drawable.visa
                            "troy" -> R.drawable.troy
                            else -> R.drawable.remove
                        }
                        val painter = rememberAsyncImagePainter(model = model)
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                color = Color.White
                            )
                        } else {
                            Image(
                                modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                painter = painter,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            // Yükleniyor animasyonu
            if (isLoading) {
                CircularProgressIndicator()
            }else{
                // Bin verisi geldiyse göster
                errorMessage.let { msg ->
                    Text(text = "error:"+msg.toString(),color = Color.White)

                }
                cardScheme.let {
                    Text(text = it.toString(), color = Color.White)
                }
                bankName.let {
                    Text(text = it.toString(), color = Color.White)
                }
            }




        }
        BackHandler(true) {
            navController.popBackStack()
        }
    }
}
