package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WishViewModel: ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")


    fun onWishTitleChange(value:String){
        wishTitleState=value
    }
    fun onWishDescriptionChange(value:String){
        wishDescriptionState=value
    }

    fun updateWish(){

    }

}