package com.example.wishlistapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),navHostController:NavHostController = rememberNavController()){
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route){
        composable(Screen.HomeScreen.route){
            HomeView(navHostController,viewModel)
        }
        composable(Screen.ItemScreen.route+"/{id}", arguments = listOf(navArgument(name = "id"){
            type= NavType.LongType
            defaultValue=0L
            nullable=false
        })){entry->
            val id=if(entry.arguments!=null) entry.arguments!!.getLong("id") else 0L
            AddEditDetailView(id = id, viewModel = viewModel, navController = navHostController)
        }
    }
}