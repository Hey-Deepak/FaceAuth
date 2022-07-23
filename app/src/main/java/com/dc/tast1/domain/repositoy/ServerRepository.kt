package com.dc.tast1.domain.repositoy

import com.dc.tast1.domain.model.Profile
import com.google.firebase.auth.FirebaseUser

interface ServerRepository {

    suspend fun createProfile(profile: Profile)

    suspend fun uploadProfilePicture(profile: Profile): String

    suspend fun getProfile(name: String) : Profile?

    suspend fun fetchProfile(it: FirebaseUser): Profile?

}