package com.equijada95.randomusercodetext.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.equijada95.randomusercodetext.presentation.navigation.NavigationController
import com.equijada95.randomusercodetext.presentation.ui.theme.RandomUserCodeTextTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserCodeTextTheme {
                NavigationController()
            }
        }
    }
}