package com.example.movies.repository

import android.net.Uri
import android.util.Log
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class ProfileRepository @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) {
    private val currentUserUid = firebaseAuth.currentUser?.uid

    private val userRef = firebaseFirestore.collection("users").document(currentUserUid ?: "")

    fun updateUser(name: String, imageUri: Uri?): Flow<Resource<Unit>> {
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

                    // Continue with the task to get the download URL
                    val downloadUri = storageRef.downloadUrl.await()

                    // Update the Firestore document with the image URL
                    val userUpdates = mapOf(
                        "name" to name,
                        "avatarUrl" to downloadUri.toString()
                    )

                    // Update the Firestore document
                    userRef.update(userUpdates).await()

                    emit(Resource.Success(Unit))
                } catch (e: Exception) {
                    Log.d("taggg","errorr : $e")
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

    fun getUserProfile(): Flow<Resource<UserProfile?>> {
        return flow {
            emit(Resource.Loading())

            if (currentUserUid != null) {
                val snapshot = userRef.get().await()
                if (snapshot.exists()) {
                    val userProfile = snapshot.toObject(UserProfile::class.java)
                    Log.d("taggg","user existsssssss $userProfile")
                    emit(Resource.Success(userProfile))
                } else {
                    emit(Resource.Error("User profile not found"))
                }
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}