package com.aslansoft.caffesist.views

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.connection.RouteException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Formatter
import java.util.Locale

data class HistOrder(
    val confirm: String,
    val payTo: Boolean,
    val date: Timestamp,
    val totalPrice: Double,
    val orderNo: String
)



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("WeekBasedYear")
@Composable
fun History(navController: NavHostController) {
    val db = Firebase.firestore
    val auth = Firebase.auth

    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    val orderList = remember {
        mutableStateListOf<HistOrder>()
    }
    LaunchedEffect (Unit){
        db.collection("orders").whereEqualTo("userId",uid)
            .get()
            .addOnSuccessListener {documents ->
               for (document in documents){
                   val orderData: Map<String,Any> = document.data
                   val confirm = orderData["confirm"].toString()
                   val payTo = orderData["payTo"] as Boolean
                   val date = orderData["date"] as Timestamp
                   val totalPrice = (orderData["totalPrice"] as Long).toDouble()

                   orderList.add(HistOrder(confirm,payTo,date,totalPrice, orderNo = document.id))
               }

            }.addOnFailureListener{
                println(it.message)
                orderList.toList()
            }
    }

    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {
        Column (modifier =Modifier.fillMaxSize() ){
            CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth().shadow(elevation = 50.dp, spotColor = Color.White),title = {Text("Geçmiş", fontSize = 30.sp, fontWeight = FontWeight.Bold,color = Color.White)}, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ))
            Spacer(Modifier.padding(vertical = 10.dp))
            LazyColumn {
                items(orderList.size){index: Int ->
                    val orders = orderList[index]
                    Column {
                        Column {
                            Text("id:${orders.orderNo}", color = MaterialTheme.colorScheme.secondary)
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            val date = Date(orders.date.seconds * 1000)
                            val simpleDateFormat = SimpleDateFormat("dd/MM/YYYY HH:mm", Locale.getDefault())
                            val formattedDate= simpleDateFormat.format(date)

                            Text(modifier = Modifier.fillMaxWidth(), text = "Date:${formattedDate}", color = MaterialTheme.colorScheme.secondary, textAlign = TextAlign.End)
                        }
                        Divider(modifier = Modifier.fillMaxWidth(1f))
                    }

                }
            }
        }

    }
}