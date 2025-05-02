package com.cirodevs.tiendaonlinefirebase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cirodevs.tiendaonlinefirebase.pages.CategoryProductPage
import com.cirodevs.tiendaonlinefirebase.screen.AuthScreen
import com.cirodevs.tiendaonlinefirebase.screen.HomeScreen
import com.cirodevs.tiendaonlinefirebase.screen.LoginScreen
import com.cirodevs.tiendaonlinefirebase.screen.SignupScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    GlobalNavigation.navController = navController

    val isLoggedIn = Firebase.auth.currentUser != null

    val firstPage = if(isLoggedIn) "home" else "auth" // esto es por si sale de la app y si vuelve se va al home

    NavHost(navController = navController, startDestination = firstPage) {

        composable ( "auth" ){
            AuthScreen(  modifier, navController )
        }
        composable ( "login" ){
            LoginScreen(  modifier , navController )
        }
        composable ( "signup" ){
            SignupScreen(  modifier, navController )
        }
        composable ( "home" ){
            HomeScreen(  modifier, navController  )
        }
        composable ( "category-products/{categoryId}" ){
            var categoryId = it.arguments?.getString("categoryId")
            CategoryProductPage(modifier, categoryId?:"")
        }
    }
}

object GlobalNavigation{
    lateinit var navController: NavHostController
}