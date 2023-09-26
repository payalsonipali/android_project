package com.example.movies.repository

import android.content.SharedPreferences
import android.net.Uri
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) : ProfileRepository {
    private val currentUserUid: String?
        get() = sharedPreferences.getString("uid", "")

    private val userRef: DocumentReference
        get() = firebaseFirestore.collection("users").document(currentUserUid ?: "")

    override fun updateUser(name: String, imageUri: Uri?): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())

            if (imageUri != null) {
                val storageRef =
                    Firebase.storage.reference.child("profile_images/${currentUserUid}")
                val uploadTask = storageRef.putFile(imageUri)

                try {
                    val taskSnapshot = uploadTask.await()
                    if (!taskSnapshot.task.isSuccessful) {
                        throw taskSnapshot.task.exception ?: Exception("Unknown upload error")
                    }

                    val downloadUri = storageRef.downloadUrl.await()

                    val userUpdates = mapOf(
                        "name" to name,
                        "avatarUrl" to downloadUri.toString()
                    )
                    userRef.update(userUpdates).await()
                    emit(Resource.Success(Unit))
                } catch (e: Exception) {
                    emit(Resource.Error(e.message.toString()))
                }
            } else {
                try {
                    val userUpdates = mapOf(
                        "name" to name
                    )
                    userRef.update(userUpdates).await()
                    emit(Resource.Success(Unit))
                } catch (e: Exception) {
                    emit(Resource.Error(e.message.toString()))
                }
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getUserProfile(): Flow<Resource<UserProfile?>> {
        return flow {
            emit(Resource.Loading())
            if (currentUserUid != null) {
                val snapshot = userRef.get().await()
                if (snapshot.exists()) {
                    val userProfile = snapshot.toObject(UserProfile::class.java)
                    emit(Resource.Success(userProfile))
                } else {
                    emit(Resource.Error("User profile not found"))
                }
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun logout() {
        removeDataFromSharedPrefrence()
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    private fun removeDataFromSharedPrefrence() {
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn")
        editor.remove("uid")
        editor.apply()
    }
}