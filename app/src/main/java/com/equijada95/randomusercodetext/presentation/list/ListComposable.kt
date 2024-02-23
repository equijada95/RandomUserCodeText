package com.equijada95.randomusercodetext.presentation.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.equijada95.domain.model.Gender
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.R
import com.equijada95.randomusercodetext.presentation.utilities.Loading
import com.equijada95.randomusercodetext.presentation.utilities.SearchBar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun List(
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

    Column {
        if (state.loading) {
            Loading()
        } else {
            SearchBar(setSearch = { viewModel.search(it) })
            ListItems(userList = state.userList, goToDetail = goToDetail, isSearching = viewModel.isSearching, refreshing = state.refreshing, loadMore = { viewModel.loadMore() })
        }
    }
}

@Composable
private fun ListItems(
    userList: List<User>,
    goToDetail: (User) -> Unit,
    isSearching: Boolean,
    refreshing: Boolean,
    loadMore: ()-> Unit
) {
    var refreshingState by remember { mutableStateOf(false) }
    refreshingState = refreshing

    LazyColumn {
        items(userList.size) {i ->
            val item = userList[i]
            if (i >= userList.size - 1 && !isSearching && !refreshingState) {
                refreshingState = true
                loadMore()
            }
            ItemView(user = item, goToDetail = goToDetail)
        }
        item {
            if (refreshingState) {
                Loading()
            }
        }
    }
}

@Composable
private fun ItemView(
    user: User,
    goToDetail: (User) -> Unit,
) {
    val painter = rememberVectorPainter(image = ImageVector.vectorResource((R.drawable.user)))
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_constraint))
            .clickable { goToDetail(user) },
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
        
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_constraint)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.name,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_list)))
            Text(
                text = user.email,
                fontSize = dimensionResource(id = R.dimen.little_text_gray).value.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_list)))
            Divider(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacer_list)))
        }
    }
    
}

@Preview(showBackground = true)
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
    ListItems(userList = list, goToDetail = {}, isSearching = false, refreshing = false, loadMore = {  })
}