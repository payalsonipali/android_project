package com.example.movies.model

data class UserProfile(
    val name: String,
    val avatarUrl: String,
    val email: String
){
    constructor():this("","","")
}

data class ProfileState(
    val isLoading: Boolean = false,
    val success: UserProfile? = null,
    val error: String = ""
)

data class UpdateProfileState(
    val isLoading: Boolean = false,
    val success: Unit? = null,
    val error: String = ""
)

