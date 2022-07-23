package com.dc.tast1.screen.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.dc.tast1.Screen
import com.dc.tast1.domain.repositoy.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localRepository: LocalRepository
): BaseViewModel() {
    private val isProfileCreatedState = mutableStateOf(false)

    fun readProfileFromPrefsAndNavigate(navHostController: NavHostController) {
        viewModelScope.launch(exceptionHandler) {
            isProfileCreatedState.value = localRepository.getIsProfileCreatedFromPrefs()
            Log.d("TAG", "readProfileFromPrefsAndNavigate: ${isProfileCreatedState.value}")
            if (isProfileCreatedState.value){
                navHostController.navigate(Screen.Welcome.route){
                    popUpTo(Screen.Splash.route) {inclusive = true}
                }
            } else {
                navHostController.navigate(Screen.Login.route){
                    popUpTo(Screen.Splash.route) {inclusive = true}
                }
            }
        }
    }
}