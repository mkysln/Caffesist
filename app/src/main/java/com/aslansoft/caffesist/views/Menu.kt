package com.aslansoft.caffesist.views

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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.classes.QRCodeViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class Products(
    val name: String,
    val photo: String,
    val price: Long,
    val type: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavHostController,documentId: String) {
    val qrCodeViewModel: QRCodeViewModel = viewModel()
    val db = Firebase.firestore
    val productsList = remember {
        mutableListOf<Products>()
    }
    val name = remember {
        mutableStateOf("")
    }
    val photo = remember {
        mutableStateOf("")
    }
    val price = remember {
        mutableStateOf(0L)
    }
    val type = remember {
        mutableStateOf("")
    }
    val basketPrice = remember {
        mutableStateOf(0L)
    }
    LaunchedEffect(key1 = Unit) {
        db.collection("Products").get().addOnSuccessListener { documents ->
            for (document in documents){
                val productData: Map<String,Any> = document.data
                name.value = productData["name"].toString()
                photo.value = productData["photo"].toString()
                price.value = productData["price"] as Long
                type.value = productData["type"].toString()
                productsList.add(Products(name.value,photo.value,price.value,type.value))
            }
        }.addOnFailureListener{exc ->
        println(exc.localizedMessage)
        productsList.toList()
        }
    }


    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        qrCodeViewModel.resetQRResult()
        Column(modifier = Modifier
            .fillMaxSize()
           , verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Hoşgeldiniz Masa $documentId", color = Color.White, fontSize = 15.sp)
            Row (modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center){

                LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                    item { 
                        Spacer(modifier = Modifier.padding(vertical = 3.dp))
                    }
                    items(productsList.size){ index ->
                        val piece = remember {
                            mutableStateOf(0)
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
                                    Image(modifier = Modifier.clip(shape = RoundedCornerShape(3.dp)),painter = painter, contentDescription ="menu photo" )
                                }
                                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                Column {
                                    Text(text = productData.name, fontSize = 17.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                    Text(text = productData.price.toString() + "₺", fontSize = 15.sp, color = Color.White)
                                }
                                Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                                      IconButton(onClick = { piece.value += 1 }) {
                                          Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
                                      }
                                    Text(text = piece.value.toString())
                                    var buttonEnabled: Boolean = false
                                    if (piece.value == 0){
                                        buttonEnabled = false
                                    }else{
                                        buttonEnabled = true
                                    }
                                    IconButton(onClick = {
                                        if (piece.value > 0){
                                            piece.value -= 1
                                        } 
                                                             },
                                        enabled = buttonEnabled) {
                                        if (piece.value == 0){
                                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.remove),contentDescription = "", tint = Color.LightGray)

                                        }else{
                                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.remove),contentDescription = "", tint = Color.White)

                                        }
                                    }

                                }
                                }

                        }
                        Spacer(modifier = Modifier.padding(vertical = 3.dp))
                        basketPrice.value = price.value * piece.value
                    }

                } )
            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(85.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom){
                BottomAppBar(actions = {
                    Text(text = "Toplam Fiyat:")
                }, floatingActionButton = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.shopping_basket), contentDescription = null )
                    }
                })
            }
        }
    }
    BackHandler(true) {
        navController.popBackStack()

    }
}