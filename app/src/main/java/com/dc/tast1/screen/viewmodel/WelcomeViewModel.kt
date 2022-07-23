package com.dc.tast1.screen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.LocalRepository
import com.dc.tast1.domain.repositoy.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
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