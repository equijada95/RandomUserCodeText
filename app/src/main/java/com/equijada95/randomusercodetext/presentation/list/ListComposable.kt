package com.equijada95.randomusercodetext.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListComposable(
    viewModel: ListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

}