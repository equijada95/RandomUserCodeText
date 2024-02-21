package com.equijada95.randomusercodetext.presentation.utilities

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.equijada95.randomusercodetext.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    title: String,
    backgroundId: Int,
    iconUserUrl: String,
    content: @Composable (PaddingValues) -> Unit
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Scaffold(
        topBar = {
            Box {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = backgroundId)),
                    painter = painterResource(R.mipmap.background_header),
                    contentDescription = "background_image",
                    contentScale = ContentScale.FillBounds
                )
                LargeTopAppBar(
                    title = {
                        IconUser(iconUserUrl)
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onBackPressedDispatcher?.onBackPressed() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    tint = Color.White,
                                    contentDescription = "Back",
                                )
                            }
                            Text(
                                text = title,
                                fontSize = dimensionResource(id = R.dimen.toolbar_detail_title).value.sp,
                                color = Color.White
                            )
                        }
                    }
                )
            }
        },
        content = content
    )
}

@Composable
private fun IconUser(iconUserUrl: String) {
    val defaultPainter = rememberVectorPainter(image = ImageVector.vectorResource((R.drawable.user)))
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.spacer_list))
            .size(dimensionResource(id = R.dimen.background_detail_icon_user_size))
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        GlideImage(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.padding_start_image_inside_box))
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.picture_list_size))
                .aspectRatio(1f)
                .clip(CircleShape),
            imageModel = { iconUserUrl },
            previewPlaceholder = defaultPainter,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
            ),
        )
    }
}