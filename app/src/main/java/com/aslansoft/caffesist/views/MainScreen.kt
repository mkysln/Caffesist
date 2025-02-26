package com.aslansoft.caffesist.views

import QrCode
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = Firebase.auth
    val cameraGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

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
    Surface {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(R.drawable.main_bg), contentScale = ContentScale.Crop, alpha = 1f),
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
                            modifier = Modifier.fillMaxSize()
                                .clickable {

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
                            modifier = Modifier.fillMaxSize()
                                .clickable {

                                },
                            painter = painterResource(R.drawable.caffesist_logo_bg_buyuk),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
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
                                    requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                                } else {
                                    navController.navigate("Camera")
                                }
                            }
                            .fillMaxSize()
                            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
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
                Text("COFFEE MEETING", fontSize = 44.sp, fontWeight = FontWeight.Bold, color = Color(183, 137, 64,
                    0x000000d2
                ))
            }

        }

    }
}


//qr menu
/*

 */


//menu button
/*

 */