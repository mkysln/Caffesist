package com.aslansoft.caffesist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aslansoft.caffesist.ui.theme.CaffesistTheme
import com.aslansoft.caffesist.views.BasketScreen
import com.aslansoft.caffesist.views.Camera
import com.aslansoft.caffesist.views.History
import com.aslansoft.caffesist.views.Login
import com.aslansoft.caffesist.views.MainScreen
import com.aslansoft.caffesist.views.Menu
import com.aslansoft.caffesist.views.NoInternetScreen
import com.aslansoft.caffesist.views.RegisterScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {

            CaffesistTheme {
                val startDestination = remember {
                    mutableStateOf("")
                }
                val systemUIcontroller = rememberSystemUiController()
                systemUIcontroller.setStatusBarColor(color = MaterialTheme.colorScheme.background)
                systemUIcontroller.setNavigationBarColor(color = MaterialTheme.colorScheme.background)
                val navController = rememberNavController()
                val auth = Firebase.auth
                val internetState = remember {
                    mutableStateOf(false)
                }

                    if (auth.currentUser != null){
                        startDestination.value = "main_screen"
                    }else{
                        startDestination.value = "login_screen"
                    }


                NavHost(navController = navController, startDestination = startDestination.value){
                    composable("login_screen"){
                        Login(navController = navController)
                    }
                    composable("register_screen"){
                        RegisterScreen(navController = navController)
                    }
                    composable("main_screen"){
                        MainScreen(navController = navController)
                    }
                    composable("camera"){
                        Camera(navController = navController)
                    }
                    composable("menu_screen/{documentId}"){backStackEntry ->
                        backStackEntry.arguments?.getString("documentId")
                            ?.let { Menu(navController = navController, documentId = it) }
                    }
                    composable("basket_screen/{totalPrice}"){backStackEntry ->
                        backStackEntry.arguments?.getString("totalPrice")
                            .let { BasketScreen(navController = navController, totalPrice = it) }

                    }
                    composable("no_internet"){
                        NoInternetScreen(navController = navController)
                    }
                    composable("history"){
                        History(navController = navController)
                    }
                }



            }
        }
    }
}





