package com.dc.tast1.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.dc.tast1.nav_graph.Screen
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.LocalRepository
import com.dc.tast1.domain.repositoy.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
): BaseViewModel() {

    val profileState = mutableStateOf<Profile?>(null)

    fun createProfile(profile: Profile, navHostController: NavHostController, sharedViewModel: SharedViewModel) {
        viewModelScope.launch(exceptionHandler) {
            showToast("Profile is Creating")
            loadingState.value = true
            if (profileState.value!!.displayPhoto.contains("file://")) {
                val downloadedUrl =
                    serverRepository.uploadProfilePicture(profile)
                serverRepository.createProfile(profile.copy(displayPhoto = downloadedUrl))
            } else {
                serverRepository.createProfile(profile)
            }
            // Save Profile to Prefs
            sharedViewModel.addSenderProfile(profile)
            saveProfileToPrefs(profile)
            saveIsProfileCreatedStatusToPrefs(true)

            loadingState.value = false
            showToast("Profile is Created")

            // Navigate to All Users Screen
            navHostController.navigate(Screen.Welcome.route){
                popUpTo(Screen.Profile.route){ inclusive = true}
            }
        }
    }


    private fun saveProfileToPrefs(profile: Profile) {
        viewModelScope.launch(exceptionHandler) {
            localRepository.saveProfileToPrefs(profile = profile)
            showToast("Profile Saved to Prefs")
        }
    }


    private fun saveIsProfileCreatedStatusToPrefs(statusOfProfile: Boolean) {
        viewModelScope.launch (exceptionHandler){
            localRepository.saveIsProfileCreatedStatusToPrefs(statusOfProfile)
            Log.d("TAG", "ProfileViewmodel, profileStatus saved to prefs ${statusOfProfile}")
        }
    }
}