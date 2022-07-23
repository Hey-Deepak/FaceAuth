package com.dc.tast1.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dc.tast1.R
import com.dc.tast1.screen.components.ErrorDialog
import com.dc.tast1.screen.components.LoadingDialog
import com.dc.tast1.screen.viewmodel.LoginViewModel
import com.dc.tast1.screen.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    launchLoginFlow: (() -> Unit) -> Unit,
    sharedViewModel: SharedViewModel
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(0.6f))

        Text(text = "Welcome to My Chat",
            modifier = Modifier.padding(16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Button(onClick = {
            //Log.d("TAG", "LoginScreen: inside LoginScreen")
            launchLoginFlow {
                Log.d("TAG", "LoginScreen: inside launchLoginFlow")
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    Log.d("TAG", "LoginScreen: $it")
                    loginViewModel.getFirebaseUser(it, navHostController,sharedViewModel)

                }
            }

        }) {
            Text(text = "Login",
                fontSize = 20.sp)
        }

        LoadingDialog(isDialogShowing = loginViewModel.loadingState.value)
        ErrorDialog(isDialogShowing = loginViewModel.showErrorState.value,
            errorMessage = loginViewModel.showErrorMessageState.value){
            loginViewModel.showErrorState.value = it
        }
    }

}