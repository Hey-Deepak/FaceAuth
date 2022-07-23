package com.dc.tast1.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dc.tast1.presentation.viewmodel.ProfileViewModel
import com.dc.tast1.presentation.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen(navController: NavHostController, profileViewModel: ProfileViewModel, welcomeViewModel: WelcomeViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        welcomeViewModel.fetchProfile()
        Log.d("TAG", "WelcomeScreen: ${welcomeViewModel.welcomeProfileState.value}")
        Toast.makeText(context, "Welcome back ${welcomeViewModel.welcomeProfileState.value!!.displayName}", Toast.LENGTH_LONG).show()
    }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            // Profile Image
            AsyncImage(model = welcomeViewModel.welcomeProfileState.value!!.displayPhoto,
                contentDescription = "",
                modifier = Modifier.size(150.dp))

            Spacer(modifier = Modifier.size(16.dp))

            // Name
            OutlinedTextField(
                value = welcomeViewModel.welcomeProfileState.value!!.displayName,
                onValueChange = {
                    welcomeViewModel.welcomeProfileState.value = welcomeViewModel.welcomeProfileState.value!!.copy(
                        displayName = it
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(fontSize = 16.sp),
                label = {
                    Text(text = "Name")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.dc.tast1.R.drawable.ic_account),
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Email
            OutlinedTextField(
                value = welcomeViewModel.welcomeProfileState.value!!.mailId,
                onValueChange = {
                    welcomeViewModel.welcomeProfileState.value = welcomeViewModel.welcomeProfileState.value!!.copy(
                        mailId = it
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = TextStyle(fontSize = 16.sp),
                label = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.dc.tast1.R.drawable.ic_email),
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Mobile Number
            OutlinedTextField(
                value = welcomeViewModel.welcomeProfileState.value!!.mobileNumber,
                onValueChange = {
                    welcomeViewModel.welcomeProfileState.value = welcomeViewModel.welcomeProfileState.value!!.copy(
                        mobileNumber = it
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(fontSize = 16.sp),
                label = {
                    Text(text = "Mobile Number")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.dc.tast1.R.drawable.ic_phone),
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Address
            OutlinedTextField(
                value = welcomeViewModel.welcomeProfileState.value!!.address,
                onValueChange = {
                    welcomeViewModel.welcomeProfileState.value = welcomeViewModel.welcomeProfileState.value!!.copy(
                        address = it
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(fontSize = 16.sp),
                label = {
                    Text(text = "Address")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.dc.tast1.R.drawable.ic_address),
                        contentDescription = ""
                    )
                }
            )
        }
}