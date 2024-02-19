package com.equijada95.data.model

import com.equijada95.data.model.location.LocationModel

data class RandomUserModel(
    val name: NameModel,
    val locationModel: LocationModel,
    val email: String,
    val gender: String,
    val pictureModel: PictureModel,
    val registered: RegisteredModel
)