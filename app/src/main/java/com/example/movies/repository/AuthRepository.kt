package com.example.movies.repository

import android.net.Uri
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    fun loginUser(email:String, password:String): Flow<Resource<AuthResult>>

    fun registerUser(email:String, password:String): Flow<Resource<AuthResult>>

    fun googleSignIn(authCredential:AuthCredential) : Flow<Resource<AuthResult>>
}