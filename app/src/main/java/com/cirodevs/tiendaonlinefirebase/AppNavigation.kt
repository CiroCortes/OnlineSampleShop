package com.cirodevs.tiendaonlinefirebase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cirodevs.tiendaonlinefirebase.screen.AuthScreen
import com.cirodevs.tiendaonlinefirebase.screen.LoginScreen
import com.cirodevs.tiendaonlinefirebase.screen.SignupScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {

        composable ( "auth" ){
            AuthScreen(  modifier, navController )
        }
        composable ( "login" ){
            LoginScreen(  modifier , navController )
        }
        composable ( "signup" ){
            SignupScreen(  modifier, navController )
        }
    }
}