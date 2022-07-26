package com.dc.tast1.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : BaseViewModel() {

    var senderProfile by mutableStateOf<Profile?>(null)

    init {
        viewModelScope.launch(exceptionHandler) {
            if (localRepository.getIsProfileCreatedFromPrefs())
                senderProfile = localRepository.getProfileFromPrefs()
        }
    }

    fun addSenderProfile(profile: Profile) {
        viewModelScope.launch {
            senderProfile = profile
        }
    }
}