package com.equijada95.data.model

import com.equijada95.data.model.location.LocationModel

data class RandomUserModel(
    val name: NameModel,
    val location: LocationModel,
    val email: String,
    val gender: String,
    val picture: PictureModel,
    val registered: RegisteredModel
)