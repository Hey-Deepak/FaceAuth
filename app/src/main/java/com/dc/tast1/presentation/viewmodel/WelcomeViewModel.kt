package com.dc.tast1.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : BaseViewModel() {
    val welcomeProfileState = mutableStateOf<Profile?>(null)
    init {
            runBlocking {
                welcomeProfileState.value = localRepository.getProfileFromPrefs()
            }
    }
    fun fetchProfile(){
        runBlocking {
            welcomeProfileState.value = localRepository.getProfileFromPrefs()
        }
    }
}