package com.aslansoft.caffesist.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun BasketScreen(navController: NavHostController, totalPrice: String?) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            totalPrice?.let { Text(text = it+"₺", color = Color.White, fontSize = 45.sp) }
            Spacer(modifier = Modifier.padding(vertical = 3.dp))
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp).fillMaxWidth().height(70.dp)){
                Text(modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    text = "Ödemeniz gereken tutar $totalPrice ₺ devam etmek istiyor musunuz?",
                    color = Color.White,
                    fontSize = 13.sp)

            }
            ElevatedButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 10.dp,
                    focusedElevation = 10.dp
                )) {
                Text(text = "Ödeme Yap")
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Geri", color = Color.White)
            }
        }
    }
    BackHandler(true) {
        navController.popBackStack()
    }
}