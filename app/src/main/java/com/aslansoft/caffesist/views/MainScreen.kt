package com.aslansoft.caffesist.views

import QrCode
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.aslansoft.caffesist.R
import com.aslansoft.caffesist.icons.Campaign
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val cameraGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val scope = rememberCoroutineScope()
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraGranted.value = true
            navController.navigate("Camera")
        } else {
            Toast.makeText(
                context,
                "Kamera İzni Vermeniz Gerekiyor",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    val sideBarState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = sideBarState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(80, 8, 12),
                drawerContentColor = Color(183, 137, 64)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(0.5f)
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Coffe Meeting",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(183, 137, 64)
                    )
                    Divider(color = Color(183, 137, 64))

                    Text(
                        "Siparişler",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(183, 137, 64)
                    )
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = ""
                            )
                        },
                        label = { Text("Geçmiş", color = Color(183, 137, 64)) },
                        selected = false,
                        onClick = { /* Handle click */ },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = Color(183, 137, 64),
                            unselectedIconColor = Color(183, 137, 64)
                        )
                    )
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = Campaign, contentDescription = "") },
                        label = { Text("Kampanyalar", color = Color(183, 137, 64)) },
                        selected = false,
                        onClick = { /* Handle click */ },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = Color(183, 137, 64),
                            unselectedIconColor = Color(183, 137, 64)

                        )
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(183, 137, 64)
                    )

                    Text(
                        "Ayarlar",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(183, 137, 64)
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings", color = Color(183, 137, 64)) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        onClick = { /* Handle click */ },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = Color.Transparent,
                            unselectedIconColor = Color(183, 137, 64),
                            selectedIconColor = Color(183, 137, 64),
                            unselectedTextColor = Color(183, 137, 64),
                            selectedTextColor = Color(183, 137, 64)
                        )
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback", color = Color(183, 137, 64)) },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        onClick = { /* Handle click */ },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = Color.Transparent,
                            unselectedIconColor = Color(183, 137, 64),
                            selectedIconColor = Color(183, 137, 64),
                            unselectedTextColor = Color(183, 137, 64),
                            selectedTextColor = Color(183, 137, 64)
                        )
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(183, 137, 64)
                    )
                    Spacer(Modifier.padding(vertical = 10.dp))
                    Row(Modifier.fillMaxWidth()
                        .clickable {
                            Firebase.auth.signOut()
                            navController.navigate("login_screen")
                        }, horizontalArrangement = Arrangement.Center) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.logout_24px), contentDescription = "", tint = Color.White)
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text("Çıkış Yap", color = Color.White) }
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        modifier = Modifier,
    ) {
        Scaffold { innerPadding ->
            Surface {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .paint(
                            painterResource(R.drawable.main_bg),
                            contentScale = ContentScale.Crop,
                            alpha = 1f
                        ),
                ) {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),

                        title = { Text("") },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,

                            ),
                        navigationIcon = {
                            Box(modifier = Modifier.size(40.dp)) {
                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            scope.launch {
                                                sideBarState.open()
                                            }
                                        },
                                    imageVector = Icons.Outlined.Menu,
                                    contentDescription = "",
                                    tint = Color.White
                                )

                            }


                        },
                        actions = {
                            Box(Modifier.size(45.dp)) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {

                                        },
                                    painter = painterResource(R.drawable.caffesist_logo_bg_buyuk),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.padding(vertical = 20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(96.dp)
                                .height(34.dp), shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color(183, 137, 64),
                                contentColor = Color.Black
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("MENÜ", fontSize = 20.sp)

                            }
                        }
                        Spacer(Modifier.padding(vertical = 10.dp))
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                        ) {


                            androidx.compose.material.Icon(
                                modifier = Modifier
                                    .border(1.dp, Color.Black, RoundedCornerShape(9.dp))
                                    .clickable {
                                        if (!cameraGranted.value) {
                                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                                        } else {
                                            navController.navigate("Camera")
                                        }
                                    }
                                    .fillMaxSize()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(9.dp)
                                    )
                                    .padding(10.dp),
                                imageVector = QrCode,
                                contentDescription = null,
                                tint = Color.Black

                            )

                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            "COFFEE MEETING",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(
                                183, 137, 64,
                                0x000000d2
                            )
                        )
                    }
                }

            }
        }
    BackHandler(true) {
        (context as? ComponentActivity)?.finish()
    }

    }
}
