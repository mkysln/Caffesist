@file:Suppress("DEPRECATION")

package com.aslansoft.caffesist.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.aslansoft.caffesist.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

data class Products(
    val name: String,
    val photo: String,
    val price: Long,
    val type: String
)
@SuppressLint("UnrememberedMutableState", "RememberReturnType", "MutableCollectionMutableState")
@Composable
fun Menu(navController: NavHostController,documentId: String) {
    val db = Firebase.firestore

    val productsList = remember{
        mutableStateListOf<Products>()
    }
    val selectedProducts = remember {
     mutableStateMapOf<Products, Int>()
    }
    val name = remember {
        mutableStateOf("")
    }
    val photo = remember {
        mutableStateOf("")
    }
    val price = remember {
        mutableLongStateOf(0L)
    }
    val type = remember {
        mutableStateOf("")
    }
    
    val isLoading = remember {
        mutableStateOf(true)
    }

    val totalPrice by remember {
        derivedStateOf {
            selectedProducts.entries.sumOf { it.key.price * it.value }
        }
    }
    val alertState = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val userName = remember {
        mutableStateOf("")
    }
    val userSurname = remember {
        mutableStateOf("")
    }
    val orderId = remember { mutableStateOf("") }
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val email = currentUser?.email
    val uid = currentUser?.uid
    LaunchedEffect(Unit) {
        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        db.firestoreSettings = settings
        try {
            email?.let {
                db.collection("users").document(it).get().addOnSuccessListener {
                    val data = it.data
                    userName.value = data?.get("name").toString()
                    userSurname.value = data?.get("surname").toString()
                    db.collection("Products").get().addOnSuccessListener { documents ->
                        for (document in documents){
                            val productData: Map<String,Any> = document.data
                            name.value = productData["name"].toString()
                            photo.value = productData["photo"].toString()
                            price.longValue = productData["price"] as Long
                            type.value = productData["type"].toString()
                            productsList.add(Products(name.value,photo.value,price.longValue,type.value))
                        }
                        isLoading.value = false
                    }.addOnFailureListener{exc ->
                        println(exc.localizedMessage)
                        productsList.toList()
                        isLoading.value = true
                    }
                }.addOnFailureListener{exception ->
                    println(exception.message)
                }
            }

        }catch (e: Exception){
            println(e.localizedMessage)
        }

    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
           , verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Hoşgeldiniz Masa $documentId", color = Color.White, fontSize = 15.sp)
            Row (modifier = Modifier
                .fillMaxWidth()
                .weight(1f), horizontalArrangement = Arrangement.Center){
                        if (!isLoading.value){
                            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                                item {
                                    Spacer(modifier = Modifier.padding(vertical = 3.dp))
                                }
                                items(productsList.size){ index ->
                                    val piece = remember {
                                        mutableIntStateOf(0)
                                    }
                                    val productData =productsList[index]
                                    val painter = rememberAsyncImagePainter(model = productData.photo)
                                    ElevatedCard(modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .padding(start = 4.dp, end = 4.dp),
                                        colors = CardDefaults.elevatedCardColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                        ), elevation = CardDefaults.elevatedCardElevation(
                                            defaultElevation = 10.dp,
                                            pressedElevation = 10.dp,
                                            focusedElevation = 10.dp
                                        )) {
                                        Row(modifier = Modifier
                                            .fillMaxSize()
                                            .padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Box (modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center){
                                                if (productData.photo.isNotEmpty()){
                                                    Image(modifier = Modifier.clip(shape = RoundedCornerShape(3.dp)),painter = painter, contentDescription ="menu photo" )
                                                }else{
                                                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
                                                }

                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                            Column {
                                                Text(text = productData.name, fontSize = 17.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                                Text(text = productData.price.toString() + "₺", fontSize = 15.sp, color = Color.White)
                                            }
                                            Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                                                IconButton(onClick = { piece.intValue += 1
                                                                        selectedProducts[productData] = piece.intValue
                                                println("Added ${productData.name}, Quantity ${piece.intValue}")
                                                }) {
                                                    Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
                                                }
                                                Text(text = piece.intValue.toString())
                                                IconButton(onClick = {
                                                    if (piece.intValue >= 0){
                                                        piece.intValue -= 1
                                                        if (piece.intValue == 0) {
                                                            selectedProducts.remove(productData)
                                                        } else {
                                                            selectedProducts[productData] = piece.intValue
                                                        }
                                                        println("Removed: ${productData.name}, Quantity: ${piece.intValue}")
                                                    }

                                                },
                                                    enabled = piece.intValue != 0) {
                                                    if (piece.intValue == 0){
                                                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.remove),contentDescription = "", tint = Color.LightGray)

                                                    }else{
                                                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.remove),contentDescription = "", tint = Color.White)

                                                    }
                                                }

                                            }
                                        }

                                    }
                                    Spacer(modifier = Modifier.padding(vertical = 3.dp))
                                }

                            } )
                        }else {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            }

                        }





            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)

                , horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom){
                println("TotalPrice: $totalPrice")
                println("Surname: ${userSurname.value}")
                BottomAppBar(modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .clip(RoundedCornerShape(3.dp)),containerColor = MaterialTheme.colorScheme.primary,actions = {
                    Text(modifier = Modifier.padding(start = 2.dp),text = "Toplam Fiyat: ${totalPrice}₺")
                }, floatingActionButton = {
                    FloatingActionButton(onClick = {
                        alertState.value = true
                    }, containerColor = Color(50,0,0)) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.shopping_basket), contentDescription = null )
                    }
                })
            }
        }
        
        if (alertState.value){
            AlertDialog(title = {
                Text(text = "Afiyet Olsun")
            },
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = Color.White,
                textContentColor = Color.White,
                iconContentColor = Color.White,
                text = {
                Text(text = "Ödemeyi onaylamak istiyor musunuz?")
            }, dismissButton = {
                ElevatedButton(onClick = { alertState.value = false},
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ) ) {
                    Text(text = "Hayır")
                }
            },onDismissRequest = { alertState.value = false }, confirmButton = {
                ElevatedButton(onClick = { if (totalPrice != 0L){
                    val orders = selectedProducts.map { (name,quantity) ->

                        mapOf(
                            "product" to name,
                            "quantity" to quantity,

                            )
                    }
                    val date = com.google.firebase.Timestamp.now()
                    val orderData = hashMapOf(
                        "products" to  orders,
                        "totalPrice" to totalPrice,
                        "table" to documentId,
                        "date" to date,
                        "confirm" to "onaylanmadı",
                        "payTo" to false,
                         "name" to userName.value,
                        "surname" to userSurname.value,
                        "userId" to uid
                    )
                    db.collection("orders").add(orderData).addOnSuccessListener {
                        orderId.value = it.id
                        print("succes")
                        alertState.value = false
                        if (orderId.value.isNotEmpty() || orderId.value != null){
                            navController.navigate("basket_screen/$totalPrice/${orderId.value}")
                        }else{
                            Toast.makeText(context,"Sipariş oluşturulurken bir hata oluştu",Toast.LENGTH_SHORT).show()
                        }

                    }
                }else{
                    Toast.makeText(context,"Lütfen Ürün Seçiniz",Toast.LENGTH_SHORT).show()
                    alertState.value = false
                } },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )) {
                    Text(text = "Onayla")
                }
            })
        }
        
    }
    BackHandler(true) {
        navController.popBackStack()

    }
}
