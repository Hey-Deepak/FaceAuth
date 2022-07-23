package com.dc.tast1.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dc.tast1.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen(showBiometric: () -> Unit, splashViewModel: SplashViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("TAG", "SplashScreen")
        Text(text = "Splash Screen")

        Spacer(modifier = Modifier.size(200.dp))

        Button(onClick = { showBiometric() }) {
            Text(text = "Try Again")
        }
        LaunchedEffect(key1 = Unit){
        showBiometric()
        }
    }
}