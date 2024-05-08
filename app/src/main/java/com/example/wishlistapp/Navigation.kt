package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),navHostController:NavHostController = rememberNavController()){
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route){
        composable(Screen.HomeScreen.route){
            HomeView(navHostController,viewModel)
        }
        composable(Screen.ItemScreen.route){
            AddEditDetailView(id = 0L, viewModel = viewModel, navController = navHostController)
        }
    }
}