package com.aslansoft.caffesist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

data class HistOrder(
    val confirm: String,
    val payTo: Boolean,
    val date: Timestamp,
    val totalPrice: Double,
    val orderNo: String
)



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

    Surface {
        LazyColumn {
            items(orderList.size){index: Int ->
                val orders = orderList[index]
                println(orders.orderNo)
            }
        }
    }
}