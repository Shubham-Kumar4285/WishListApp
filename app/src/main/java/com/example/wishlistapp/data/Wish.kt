package com.example.wishlistapp.data

data class Wish(
    val id :Long =0L,
    val title:String="",
    val description:String=""
)
object Dummywish{
    val wishlist= listOf<Wish>(Wish(1,"Cake","Cake is lie"),
        Wish(2,"Chocolate","It is sweet"),
        Wish(3,"Candies","like it"),
        Wish(4,"ice","very cold"))
}
