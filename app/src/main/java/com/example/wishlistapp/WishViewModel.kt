package com.example.wishlistapp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository:WishRepository = Graph.repository
): ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")



    fun onWishTitleChange(value:String){
        wishTitleState=value
    }
    fun onWishDescriptionChange(value:String){
        wishDescriptionState=value
    }
    lateinit var getAllWishes:Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes=wishRepository.getWishesByPosition()
        }
    }
    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }
    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }
    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id)
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }
    fun updateItems(items: List<Wish>) = viewModelScope.launch(Dispatchers.IO) {
        getAllWishes=wishRepository.updateAll(items)
    }

}