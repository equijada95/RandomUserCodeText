package com.equijada95.randomusercodetext.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.presentation.utilities.LoadingComponent
import com.equijada95.randomusercodetext.presentation.utilities.SearchBar
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListComposable(
    viewModel: ListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(state.refreshing, {
        //refresh()
    })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column {
            SearchBar(setSearch = {  })
            if (state.loading) {
                LoadingComponent()
            } else {
                ListItems(userList = state.userList, goToDetail = {  })
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
    Row(modifier = Modifier.clickable { goToDetail(user) }) {
        GlideImage(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            imageModel = user.picture,
            contentScale = ContentScale.Crop,
        )
        
        Column {
            Text(text = user.name)
            Text(text = user.email)
        }
    }
    
}