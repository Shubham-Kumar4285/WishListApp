package com.example.wishlistapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FractionalThreshold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Dummywish
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.ui.theme.Pink40
import com.example.wishlistapp.ui.theme.greyNew

import org.burnoutcrew.reorderable.NoDragCancelledAnimation
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context= LocalContext.current

    Scaffold(

        topBar = {AppBarView(title = "WishList App"
        , onBackClick = {})
        }, floatingActionButton ={
            FloatingActionButton(
                modifier = Modifier.padding(all=20.dp),
                onClick = {
                //add icon
                navController.navigate(Screen.ItemScreen.route +"/0L")
                },
                shape = CircleShape,
                contentColor = Color.White,
                containerColor = Color.Black
                 ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)

                
            }
            
            
        }) { it ->
//        val wishList=viewModel.getAllWishes.collectAsState(initial = listOf())
        val wishList by viewModel.getAllWishes.collectAsState(initial = emptyList())





        val state = rememberReorderableLazyListState(
            dragCancelledAnimation = NoDragCancelledAnimation(),
            onMove = { from, to ->
            if(to.index!=from.index){
                val updatedList = wishList.toMutableList()
                val draggedItem= updatedList[from.index]
                draggedItem.position=to.index
                val toItem= updatedList[to.index]
                toItem.position=from.index
                updatedList[from.index]=toItem
                updatedList[to.index]=draggedItem
                viewModel.updateItems(updatedList)


            }
        })


//
//        var text=""
//
//        wishList.forEach { wish->
//            text+=wish.title+" $ "
//        }
//        Log.d("My Tag", text)





        LazyColumn(state= state.listState,
            modifier = Modifier
                .padding(it)
                .reorderable(state)
                .detectReorderAfterLongPress(state)){
            items(wishList, key = {wish->wish.id}){
                wish->


                ReorderableItem(state, key = wish.id,defaultDraggingModifier = Modifier) { isDragging ->


                val dismissState= rememberDismissState(
                    confirmStateChange = {
                        if(it==DismissValue.DismissedToStart){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                                 val color by animateColorAsState(
                                     if(dismissState.dismissDirection==DismissDirection.EndToStart)
                                     colorResource(id = R.color.app_bar_color) else Color.Transparent, label = ""
                                 )
                                val  alignment =Alignment.CenterEnd
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp), contentAlignment = alignment){
                                    Icon(Icons.Default.Delete, contentDescription = "Delete icon", tint = Color.White)
                                }
                    } ,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.8f)}
                ) {

                        val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                        WishListItem(wish = wish,isDragging=isDragging, onClick =  {
                            val id = wish.id
                            navController.navigate(Screen.ItemScreen.route + "/$id") })
                }



            }


                    }
                }
    }


            }


@Composable
fun WishListItem(wish: Wish,isDragging:Boolean, onClick:()->Unit) {
    val color =if(isDragging && isSystemInDarkTheme()){
        greyNew}else if (isDragging){
        Pink40}else
    {MaterialTheme.colorScheme.surfaceBright}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .clickable {
                onClick()
            },
        shape = RectangleShape,
        colors = CardColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.inverseSurface,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
        ),
        elevation = CardDefaults.cardElevation(10.dp),

        ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)

        }

    }
}


