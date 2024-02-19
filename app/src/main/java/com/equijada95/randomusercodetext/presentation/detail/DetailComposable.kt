package com.equijada95.randomusercodetext.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.presentation.utilities.TopBackBar
import com.equijada95.randomusercodetext.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailComposable(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        TopBackBar(title = user.name)
        Text(text = user.name)
        Text(text = user.email)
        val coordinates = try {
            LatLng(user.latitude.toDouble(), user.longitude.toDouble())
        } catch (_: NumberFormatException) {
            null
        }
        coordinates?.let {
            AddressMap(it)
        }
    }
}

@Composable
private fun AddressMap(coordinates: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = coordinates),
            title = stringResource(id = R.string.address),
        )
    }
}