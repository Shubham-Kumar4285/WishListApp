package com.example.wishlistapp


import android.content.res.Resources.Theme
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Dummywish
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
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
        val wishList=viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier.padding(it)){
            items(wishList.value, key = {wish->wish.id}){
                wish->
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
                    dismissThresholds = {FractionalThreshold(0.8f)},
                    dismissContent = {
                        WishListItem(wish = wish) {
                        val id=wish.id
                        navController.navigate(Screen.ItemScreen.route +"/$id")
                    }

                })


            }

        }

    }


}

@Composable
fun WishListItem(wish: Wish, onClick:()->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .clickable {
                onClick()
            },
        shape= RectangleShape,
        colors = CardColors(containerColor = MaterialTheme.colorScheme.surfaceBright, contentColor = MaterialTheme.colorScheme.inverseSurface,disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(10.dp),

    ){
        Column(modifier=Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)

        }

    }

}
@Preview(showBackground = true)
@Composable
fun itemprev(){

    WishListItem(wish = Wish(12,"ddsfs","sdfsf"),{})
}