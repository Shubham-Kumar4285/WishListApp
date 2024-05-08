package com.example.wishlistapp

sealed class Screen(val route:String) {
    object HomeScreen:Screen("home_screen")
    object ItemScreen:Screen("item_screen")
}