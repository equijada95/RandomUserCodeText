package com.equijada95.randomusercodetext.presentation.detail

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(
    user: User
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val painter = rememberVectorPainter(image = ImageVector.vectorResource((R.drawable.user)))

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.padding_constraint))
                                .size(dimensionResource(id = R.dimen.picture_list_size))
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            imageModel = { user.picture },
                            previewPlaceholder = painter,
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                            ),
                        )
                    }
                },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onBackPressedDispatcher?.onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )

                        }
                        Text(
                            text = user.name,
                            fontSize = dimensionResource(id = R.dimen.toolbar_detail_title).value.sp,
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        DetailView(user = user, innerPadding = innerPadding)
    }
}

@Composable
private fun DetailView(user: User, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {

        Cell(drawableRsource = R.drawable.user, title = stringResource(id = R.string.name), description = user.name)
        Cell(drawableRsource = R.drawable.mail, title = stringResource(id = R.string.email), description = user.email)
        user.gender.cell()
        Cell(drawableRsource = R.drawable.calendar, title = stringResource(id = R.string.registered_date), description = user.registeredDate)
        Cell(drawableRsource = R.drawable.call, title = stringResource(id = R.string.telephone), description = user.phone)
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
private fun Cell(drawableRsource: Int, title: String, description: String) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_constraint)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = drawableRsource),
            contentDescription = null,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_icon_detail))
                .size(
                    dimensionResource(id = R.dimen.icon_detail_size),
                )
        )

        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_constraint))
        ) {
            Text(
                text = title,
                fontSize = dimensionResource(id = R.dimen.little_text_gray).value.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_constraint)))
            Text(text = description)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_constraint)))
            Divider()
        }
    }
}

@Composable
private fun AddressMap(coordinates: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 10f)
    }
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_constraint)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_icon_detail))
                .size(
                    dimensionResource(id = R.dimen.icon_detail_size),
                )
        )
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_constraint))
        ) {
            Text(
                text = stringResource(id = R.string.address),
                fontSize = dimensionResource(id = R.dimen.little_text_gray).value.sp,
                color = Color.Gray)
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.height_google_map)),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = coordinates),
                    title = stringResource(id = R.string.address),
                )
            }
        }
    }

}

@Composable
private fun Gender.cell() =
    when(this) {
        Gender.FEMALE -> Cell(drawableRsource = R.drawable.female, title = stringResource(id = R.string.gender), description = stringResource(id = R.string.female))
        Gender.MALE -> Cell(drawableRsource = R.drawable.male, title = stringResource(id = R.string.gender), description = stringResource(id = R.string.male))
        Gender.UNKNOWN -> Cell(drawableRsource = R.drawable.unknown, title = stringResource(id = R.string.gender), description = stringResource(id = R.string.female))
    }

@Preview
@Composable
fun DetailPreview() {
    Detail(user = User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),)
}
