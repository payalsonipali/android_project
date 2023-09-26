package com.example.movies.viewmodel

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.GoogleSignInState
import com.example.movies.model.SignInState
import com.example.movies.repository.AuthRepository
import com.example.movies.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(val authRepository: AuthRepository, val sharedPreferences:SharedPreferences) : ViewModel() {

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    fun googleSignIn(authCredential: AuthCredential) = viewModelScope.launch {
        authRepository.googleSignIn(authCredential).collect() { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }

                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }

                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
            }
        }
    }

    fun loginUSer(email: String, password: String) = viewModelScope.launch {
        authRepository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Login successfully"))
                }

                is Resource.Error -> {
                    _signInState.send(SignInState(isError = result.message))
                }

                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
            }
        }

    }

    fun callLauncher(data: Intent?){
        val account = GoogleSignIn.getSignedInAccountFromIntent(data)
        try{
            val result = account.getResult(ApiException::class.java)
            val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
            googleSignIn(credentials)
        } catch (e: ApiException){
            Log.d("taggg","Something went wrong $e")
        }
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

}