package com.equijada95.randomusercodetext.presentation.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.R
import com.equijada95.randomusercodetext.presentation.utilities.LoadingComposable
import com.equijada95.randomusercodetext.presentation.utilities.SearchBar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListComposable(
    viewModel: ListViewModel = hiltViewModel(),
    goToDetail: (User) -> Unit
) {

    val state by viewModel.state.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                when (event) {
                    is ListViewModel.Event.Error ->
                        Toast.makeText(
                            context,
                            getString(context, event.messageId),
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    val pullRefreshState = rememberPullRefreshState(state.refreshing, {
        //refresh()
    })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column {
            SearchBar(setSearch = { viewModel.search(it) })
            if (state.loading) {
                LoadingComposable()
            } else {
                ListItems(userList = state.userList, goToDetail = goToDetail)
            }
        }
        PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun ListItems(
    userList: List<User>,
    goToDetail: (User) -> Unit,
) {
    LazyColumn {
        items(
            items = userList, itemContent = { user ->
                ItemView(user, goToDetail)
            }
        )
    }
}

@Composable
private fun ItemView(
    user: User,
    goToDetail: (User) -> Unit,
) {
    val painter = rememberVectorPainter(image = ImageVector.vectorResource((R.drawable.user_placeholder)))
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_constraint))
            .clickable { goToDetail(user) }
    ) {
        GlideImage(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.picture_list_size))
                .aspectRatio(1f)
                .clip(CircleShape),
            imageModel = { user.picture },
            previewPlaceholder = painter,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
            ),
        )
        
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_constraint))
        ) {
            Text(text = user.name)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_constraint)))
            Text(text = user.email)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_constraint)))
            Divider()
        }
    }
    
}

@Preview
@Composable
fun ListPreview() {
    val list = listOf(
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
        User(gender = Gender.MALE, name = "Pablo Garcia", email = "pablo@gmail.com", latitude = "-69.8246", longitude = "134.8719", picture = "https://randomuser.me/api/portraits/men/75.jpg", registeredDate = "2007-07-09T05:51:59.390Z", phone = "(272) 790-0888"),
    )
    ListItems(userList = list, goToDetail = {})
}