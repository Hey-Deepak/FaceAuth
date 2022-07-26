package com.dc.tast1.data.remote

import android.content.SharedPreferences
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.LocalRepository
import com.google.gson.Gson

class LocalRepositoryImp(
    private val prefs: SharedPreferences,
) : LocalRepository {


    override suspend fun saveProfileToPrefs(profile: Profile) {
        prefs.edit().putString("profile", Gson().toJson(profile)).apply()
    }

    override suspend fun getProfileFromPrefs(): Profile {
        val profileJson = prefs.getString("profile", null)
        return Gson().fromJson(profileJson, Profile::class.java)
    }

    override suspend fun saveLoginStatusToPrefs(loginStatus: Boolean) {
        prefs.edit().putBoolean("loginStatus", loginStatus).apply()
    }

    override suspend fun getLoginStatusFromPrefs(): Boolean {

        return prefs.getBoolean("loginStatus", false)
    }

    override suspend fun saveIsProfileCreatedStatusToPrefs(status: Boolean) {
        prefs.edit().putBoolean("isProfileCreated", status).apply()
    }

    override suspend fun getIsProfileCreatedFromPrefs(): Boolean {
        return prefs.getBoolean("isProfileCreated", false)
    }
}