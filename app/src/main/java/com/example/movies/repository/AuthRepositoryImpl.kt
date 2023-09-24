package com.example.movies.repository

import android.net.Uri
import android.util.Log
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore,
    val firbaseStorage: FirebaseStorage
) : AuthRepository {

    private val currentUserUid = firebaseAuth.currentUser?.uid
    private val userRef = firebaseFirestore.collection("users").document(currentUserUid ?: "")

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            // Add the user's profile information to Firestore
            if (result.user != null) {
                val avatar = getYourDefaultAvatarUrl()
                val userProfile = UserProfile("", avatar, email)
                val uid = result.user!!.uid
                val userDocument = firebaseFirestore.collection("users").document(uid)
                userDocument.set(userProfile).await()
            }
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun googleSignIn(authCredential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithCredential(authCredential).await()
            if (result.user != null) {
                val avatar = getYourDefaultAvatarUrl()
                val email = result.user!!.email!!
                val userProfile = UserProfile("", avatar, email)
                val uid = result.user!!.uid
                val userDocument = firebaseFirestore.collection("users").document(uid)
                userDocument.set(userProfile).await()
            }
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    private suspend fun getYourDefaultAvatarUrl(): String {
        val defaultAvatarReference = firbaseStorage.reference.child("profile.png")
        return defaultAvatarReference.downloadUrl.await().toString()
    }

    override fun updateUserProfile(name: String, imageUri: Uri?): Flow<Resource<Unit>> {
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

    override fun userProfile(): Flow<Resource<UserProfile>> {
        return flow {
            emit(Resource.Loading())

            if (currentUserUid != null) {
                val snapshot = userRef.get().await()
                if (snapshot.exists()) {
                    val userProfile = snapshot.toObject(UserProfile::class.java)
                    Log.d("taggg","user existsssssss $userProfile")
                    if(userProfile != null){
                        emit(Resource.Success(userProfile))
                    }
                } else {
                    emit(Resource.Error("User profile not found"))
                }
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

}