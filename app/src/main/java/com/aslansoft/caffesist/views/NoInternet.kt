package com.aslansoft.caffesist.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NoInternetScreen(navController: NavHostController) {

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(modifier = Modifier.size(100.dp),imageVector = Icons.Outlined.Info, contentDescription = "", tint = Color.White)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(text = "İNTERNET YOK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(modifier = Modifier.width(320.dp),text = "Lütfen internet bağlantınızı kontrol edin", color = Color.White)
            }
    }
}

fun isNetworkAvailable(context: Context): Boolean{
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when{
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}


@Composable
fun NetworkStatusChecker(onNetworkStatusChanged: (Boolean) -> Unit) {
    val context = LocalContext.current
    val isNetworkAvailable = remember { mutableStateOf(isNetworkAvailable(context)) }

    LaunchedEffect(Unit) {
        onNetworkStatusChanged(isNetworkAvailable.value)
    }
}