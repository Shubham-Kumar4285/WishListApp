package com.example.wishlistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch


@Composable
fun AddEditDetailView(
    id:Long,
    viewModel:WishViewModel,
    navController: NavController
){  val snackMessage=remember{ mutableStateOf("") }
    val scope= rememberCoroutineScope()
    val scaffoldState= rememberScaffoldState()
    if(id!=0L){
        val currentWish = viewModel.getWishById(id)
            .collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState=currentWish.value.title
        viewModel.wishDescriptionState=currentWish.value.description

    }else{
        viewModel.wishTitleState=""
        viewModel.wishDescriptionState=""

    }




    Scaffold(
        scaffoldState=scaffoldState,
        topBar = {
        AppBarView(
            title =
            if (id != 0L) stringResource(id = R.string.update_name) else stringResource(id = R.string.add_name)
        )
        {navController.navigateUp()}

    }) { item ->
        Column(modifier = Modifier
            .padding(item)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChanged = {
                viewModel.onWishTitleChange(it)
            } )
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Value", value = viewModel.wishDescriptionState, onValueChanged = {
                viewModel.onWishDescriptionChange(it)
            } )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                             if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                                    if(id!=0L){
                                        viewModel.updateWish(
                                            Wish(id,viewModel.wishTitleState.trim(),viewModel.wishDescriptionState.trim())
                                        )

                                    }else{
                                      viewModel.addWish(Wish(title=viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim()))
                                        snackMessage.value="Wish has been added"
                                    }
                             }else{
                                 snackMessage.value="Enter the fields to create a Wish"
                             }

                            scope.launch {
                                //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                                navController.navigateUp()
                            }
                             }, modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                , colors = ButtonColors(containerColor = colorResource(id = R.color.app_bar_color), disabledContainerColor = colorResource(id = R.color.app_bar_color), contentColor = Color.White, disabledContentColor = Color.White)
            ) {

                Text(text = if (id != 0L) "Update" else "Add")
            }

        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(
    label:String,
    value:String,
    onValueChanged:(String)->Unit
){
    OutlinedTextField(value = value, onValueChange = onValueChanged,label={ Text(text = label, color = Color.Black)}, modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = colorResource(id = R.color.label_focused),
            unfocusedTextColor = colorResource(id = R.color.label_unfocussed),
            focusedBorderColor = colorResource(id = R.color.border_focused),
            unfocusedBorderColor = colorResource(id = R.color.border_unfocussed)
        )
    )

}

