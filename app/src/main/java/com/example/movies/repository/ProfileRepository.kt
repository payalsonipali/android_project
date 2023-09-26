package com.example.movies.repository

import android.net.Uri
import com.example.movies.model.UserProfile
import com.example.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository{
    fun updateUser(name: String, imageUri: Uri?): Flow<Resource<Unit>>
    fun getUserProfile(): Flow<Resource<UserProfile?>>
    fun logout()
}