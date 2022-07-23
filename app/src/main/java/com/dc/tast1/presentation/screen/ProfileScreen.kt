package com.dc.tast1.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dc.tast1.R
import com.dc.tast1.presentation.screen.components.ErrorDialog
import com.dc.tast1.presentation.screen.components.LoadingDialog
import com.dc.tast1.presentation.screen.components.ShowToast
import com.dc.tast1.presentation.viewmodel.ProfileViewModel
import com.dc.tast1.presentation.viewmodel.SharedViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navHostController: NavHostController,
    launchImagePickerFlow: ()->Unit,
    sharedViewModel: SharedViewModel
) {

    // Set Profile State
    profileViewModel.profileState.value = sharedViewModel.senderProfile

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp),
            contentAlignment = Alignment.BottomEnd
        ) {


            AsyncImage(
                model = profileViewModel.profileState.value!!.displayPhoto,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )

            // Profile Picture
            Box(
                modifier = Modifier.size(54.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.ic_add_image_2),
                    contentDescription = "Add Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            launchImagePickerFlow()
                        }
                )
            }
        }

        // Name
        OutlinedTextField(
            value = profileViewModel.profileState.value!!.displayName,
            onValueChange = {
                sharedViewModel.senderProfile = profileViewModel.profileState.value!!.copy(
                    displayName = it
                )
            },
            label = { Text("Enter Your Name", fontSize = 16.sp) },
            maxLines = 1,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

        // Mail Id
        OutlinedTextField(
            value = profileViewModel.profileState.value!!.mailId,
            onValueChange = {
                sharedViewModel.senderProfile = profileViewModel.profileState.value!!.copy(
                    mailId = it
                )
            },
            label = { Text("Enter Your Mail Id", fontSize = 16.sp) },
            maxLines = 1,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        // Mobile Number
        OutlinedTextField(
            value = profileViewModel.profileState.value!!.mobileNumber,
            onValueChange = {
                sharedViewModel.senderProfile = profileViewModel.profileState.value!!.copy(
                    mobileNumber = it
                )
            },
            label = { Text("Enter Your Mobile Number", fontSize = 16.sp) },
            maxLines = 1,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

        // Address
        OutlinedTextField(
            value = profileViewModel.profileState.value!!.address,
            onValueChange = {
                sharedViewModel.senderProfile = profileViewModel.profileState.value!!.copy(
                    address = it
                )
            },
            label = { Text("Enter Your Address", fontSize = 16.sp) },
            maxLines = 1,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        // Register Button
        Button(
            onClick = {
                createProfile(profileViewModel, navHostController, sharedViewModel)
            },
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = "Done", fontSize = 16.sp)
        }

        // Dialogs
        LoadingDialog(isDialogShowing = profileViewModel.loadingState.value)
        ErrorDialog(
            isDialogShowing = profileViewModel.showErrorState.value,
            errorMessage = profileViewModel.showErrorMessageState.value
        ) {
            profileViewModel.showErrorState.value = it
        }
        ShowToast(message = profileViewModel.showToastMessageState.value) {
            profileViewModel.showToastState.value = it
        }

    }

}

fun createProfile(
    profileViewModel: ProfileViewModel,
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    Log.d("TAG", "createProfile: ${profileViewModel.profileState.value}")
    profileViewModel.createProfile(
        profileViewModel.profileState.value!!,
        navHostController,
        sharedViewModel
    )

}