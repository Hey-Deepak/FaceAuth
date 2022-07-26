package com.dc.tast1.domain.repositoy

import com.dc.tast1.domain.model.Profile

interface LocalRepository {


    //Profile
    suspend fun saveProfileToPrefs(profile: Profile)
    suspend fun getProfileFromPrefs(): Profile

    // Status (Is?)
    suspend fun saveLoginStatusToPrefs(loginStatus: Boolean)
    suspend fun saveIsProfileCreatedStatusToPrefs(status: Boolean)

    suspend fun getLoginStatusFromPrefs(): Boolean
    suspend fun getIsProfileCreatedFromPrefs(): Boolean

}