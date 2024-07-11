package com.aslansoft.caffesist.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun BasketScreen(navController: NavHostController, totalPrice: String?) {
    val db = Firebase.firestore

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {

        }
    }
    BackHandler(true) {
        navController.popBackStack()
    }
}