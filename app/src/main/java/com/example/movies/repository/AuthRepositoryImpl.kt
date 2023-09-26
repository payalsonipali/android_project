package com.example.movies.repository

import android.content.SharedPreferences
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firbaseStorage: FirebaseStorage,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
            if (uid != null) {
                addDataToSharedPreference(uid)
            }
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                val uid = result.user!!.uid
                val userDocument = firebaseFirestore.collection("users").document(uid)
                val avatar = getYourDefaultAvatarUrl()
                val userProfile = UserProfile("", avatar, email)
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
                val uid = result.user!!.uid
                val userDocument = firebaseFirestore.collection("users").document(uid)

                val userDocumentSnapshot = userDocument.get().await()
                if (!userDocumentSnapshot.exists()) {
                    val avatar = getYourDefaultAvatarUrl()
                    val email = result.user!!.email!!
                    val userProfile = UserProfile("", avatar, email)
                    userDocument.set(userProfile).await()
                }
                addDataToSharedPreference(uid)
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

    private fun addDataToSharedPreference(uid: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("uid", uid)
        editor.apply()
    }
}