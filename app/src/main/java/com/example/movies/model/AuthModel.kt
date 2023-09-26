package com.example.movies.model

import com.google.firebase.auth.AuthResult

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)

data class GoogleSignInState(
    val loading: Boolean = false,
    val success: AuthResult? = null,
    val error: String = ""
)
