package com.example.movies.model

data class UserProfile(
    val name: String,
    val avatarUrl: String,
    val email: String
){
    constructor():this("","","")
}
