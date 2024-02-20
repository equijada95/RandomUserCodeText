package com.equijada95.randomusercodetext.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.equijada95.domain.model.User
import com.equijada95.randomusercodetext.presentation.detail.Detail
import com.equijada95.randomusercodetext.presentation.list.List
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NavigationController(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Destinations.List.route) {
        composable(Destinations.List.route) {
            List { user ->
                user.encode()?.let { encoded ->
                    navController.navigate(Destinations.Detail.createRoute(encoded))
                }
            }
        }
        composable(Destinations.Detail.route) { navBackEntry ->
            val encoded = navBackEntry.arguments?.getString("user") ?: return@composable
            encoded.decode(User::class.java)?.let { user ->
                Detail(user = user)
            }
        }
    }
}

sealed class Destinations(
    val route: String
) {
    object List: Destinations("List")
    object Detail: Destinations("Detail/{user}") {
        fun createRoute(userJson: String) = "Detail/$userJson"
    }
}

private fun <T> T.encode(): String? {
    val json = Gson().toJson(this)
    return URLEncoder.encode(json, "UTF-8")
}

/** esta funcion recoge el string encodado, lo desencripta y lo convierte en el objeto indicado en el type */
private fun <A> String.decode(type: Class<A>): A? {
    val decoded = URLDecoder.decode(this, "UTF-8")
    return Gson().fromJson(decoded, type)
}