package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id :Long =0L,
    @ColumnInfo(name ="title")
    val title:String="",
    @ColumnInfo(name ="description")
    val description:String=""
)
object Dummywish{
    val wishlist= listOf<Wish>(Wish(1,"Cake","Cake is lie"),
        Wish(2,"Chocolate","It is sweet"),
        Wish(3,"Candies","like it"),
        Wish(4,"ice","very cold"))
}
