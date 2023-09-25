package com.example.movies.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.ProfileState
import com.example.movies.model.UpdateProfileState
import com.example.movies.repository.AuthRepository
import com.example.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    val _updatedProfileState = mutableStateOf(UpdateProfileState())
    val updatedProfileState: State<UpdateProfileState> = _updatedProfileState

    fun updateUserProfile(name: String, imageUri: Uri?) = viewModelScope.launch {
        authRepository.updateUserProfile(name, imageUri).collect() { result ->
            when (result) {
                is Resource.Success -> {
                    _updatedProfileState.value = UpdateProfileState(success = result.data)
                }

                is Resource.Error -> {
                    _updatedProfileState.value = UpdateProfileState(error = result.message!!)
                }

                is Resource.Loading -> {
                    _updatedProfileState.value = UpdateProfileState(isLoading = true)
                }
            }
        }
    }

    fun getUserProfile() = viewModelScope.launch {
        authRepository.userProfile().collect() { result ->
            when (result) {
                is Resource.Success -> {
                    _profileState.value = ProfileState(success = result.data)
                }

                is Resource.Error -> {
                    _profileState.value = ProfileState(error = result.message!!)
                }

                is Resource.Loading -> {
                    _profileState.value = ProfileState(isLoading = true)
                }
            }
        }
    }
}