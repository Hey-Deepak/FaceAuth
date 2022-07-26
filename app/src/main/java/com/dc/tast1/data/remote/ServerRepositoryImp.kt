package com.dc.tast1.data.remote

import androidx.core.net.toUri
import com.dc.tast1.domain.model.Profile
import com.dc.tast1.domain.repositoy.ServerRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await


class ServerRepositoryImp() : ServerRepository {

    private val firestoreDatabaseRef = Firebase.firestore
    private val storageRef = Firebase.storage.reference

    override suspend fun createProfile(profile: Profile) {
        firestoreDatabaseRef.collection("Profiles").document(profile.mailId).set(profile).await()
    }

    override suspend fun uploadProfilePicture(profile: Profile): String {
        storageRef.child("images/${profile.mailId}").putFile(profile.displayPhoto.toUri()).await()
        val photoPath = storageRef.child("images/${profile.mailId}").downloadUrl.await()
        return photoPath.toString()
    }


    override suspend fun getProfile(name: String): Profile? {
        return firestoreDatabaseRef.collection("Profiles").document(name).get().await()
            .toObject(Profile::class.java)

    }

    override suspend fun fetchProfile(it: FirebaseUser): Profile? {
        return firestoreDatabaseRef.collection("Profiles").document(it.email.toString()).get()
            .await()
            .toObject(Profile::class.java)
    }

}