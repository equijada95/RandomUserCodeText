package com.equijada95.randomusercodetext.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.presentation.utilities.TopBackBar

@Composable
fun DetailComposable(
    user: User
) {
    TopBackBar(title = user.name)
    Text(text = user.name)
    Text(text = user.email)
}