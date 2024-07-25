package com.aslansoft.caffesist.views

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
    ) {isGranted ->
        if (isGranted){
            cameraGranted.value = true
            navController.navigate("Camera")
        }else{
            Toast.makeText(context,
                "Kamera İzni Vermeniz Gerekiyor",
                Toast.LENGTH_SHORT).show()
        }

    }
    Surface(color = MaterialTheme.colorScheme.background) {


        Column(modifier = Modifier
            .fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ElevatedCard(modifier = Modifier
                    .height(200.dp)
                    .width(250.dp)
                    .padding(
                        top = 3.dp,
                        start = 3.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    onClick = {   if (!cameraGranted.value){
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }else{
                        navController.navigate("Camera")
                    } }) {
                    Row {
                        Column(modifier = Modifier
                            .width(175.dp)
                            .fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Row {

                                Text(text = "Menü", fontSize = 35.sp, fontWeight = FontWeight.Bold)

                            }
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                            Row {
                                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                Text(text = "Menüye erişmek için qr kod okutun", )

                            }

                        }
                        Column(modifier = Modifier
                            .width(70.dp)
                            .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(modifier = Modifier
                                .fillMaxSize()
                                ,imageVector = ImageVector.vectorResource(id = R.drawable.qr_code_24px), contentDescription = null)
                        }
                    }



                }
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                Column {
                    ElevatedCard(modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .padding(top = 3.dp, end = 3.dp), colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ), onClick = {/*TODO*/}){
                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                            Text(text = "Ayarlar")
                        }

                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ElevatedCard(modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .padding(top = 3.dp, end = 3.dp), colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                        onClick = {navController.navigate("history")}){
                        Row (modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                            Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null)
                            Text(text = "Geçmiş")
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ElevatedCard(modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .padding(top = 3.dp, end = 3.dp), colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                    ),
                        onClick = {auth.signOut()
                        navController.navigate("login_screen")}){
                        Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.logout_24px), contentDescription = "")
                            Text(text = "Çıkış Yap")    
                        }
                        
                    }

                }

            }
            val samplePhotos = listOf(
                R.drawable.sayfa_1,
                R.drawable.sayfa_2,
                R.drawable.sayfa_3,
                R.drawable.sayfa_4,
            )
            val pagerState = com.google.accompanist.pager.rememberPagerState()

            Spacer(modifier = Modifier.padding(vertical = 3.dp))
            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(start = 3.dp, end = 3.dp),
                colors = CardDefaults.elevatedCardColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary
                )) {
             PhotoMenu(photoList = samplePhotos, pagerState = pagerState)
            }
            Spacer(modifier = Modifier.padding(vertical = 3.dp))

            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 3.dp, end = 3.dp),
                onClick = { /*TODO*/ },
                colors = CardDefaults.elevatedCardColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary
                )) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.padding(top = 3.dp, start = 3.dp).fillMaxWidth().height(50.dp)) {
                        Text(text = "Yaklaşan Etkinlikler")
                    }
                    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "Yaklaşan Etkinlik Bulunmamakta", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }


            }

        }

    }



}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoMenu(photoList: List<Int>,pagerState: com.google.accompanist.pager.PagerState) {
    val currentPage = remember {
        mutableStateOf(0)
    }
    val changeInterval = 3000L
    LaunchedEffect(Unit) {
        while (true){
            delay(changeInterval)
            currentPage.value = (currentPage.value + 1) % photoList.size
            pagerState.animateScrollToPage(currentPage.value)
        }
    }
    com.google.accompanist.pager.HorizontalPager(modifier = Modifier
        .fillMaxSize()
        .clip(shape = RoundedCornerShape(30.dp)),state = pagerState,count = photoList.size) { photo ->
        Image(modifier = Modifier
            .fillMaxSize()
            .padding(start = 2.dp, end = 2.dp),painter = painterResource(id = photoList[photo]), contentDescription ="" )
    }
}