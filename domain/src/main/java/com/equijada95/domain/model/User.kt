package com.equijada95.domain.model

data class User(
    val name: String,
    val latitude: String,
    val longitude: String,
    val email: String,
    val gender: Gender,
    val picture: String,
    val registeredDate: String,
    val phone: String
)
