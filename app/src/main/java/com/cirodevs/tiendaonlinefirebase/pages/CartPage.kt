package com.cirodevs.tiendaonlinefirebase.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cirodevs.tiendaonlinefirebase.AppUtil
import com.cirodevs.tiendaonlinefirebase.GlobalNavigation
import com.cirodevs.tiendaonlinefirebase.components.CartItemsView
import com.cirodevs.tiendaonlinefirebase.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())

    }

    DisposableEffect (key1 = Unit ) {
        var listener = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener {it, _ ->
                if(it!=null){
                    val result = it.toObject(UserModel::class.java)
                    if(result != null){
                        userModel.value = result
                    }
                }
            }
        onDispose {
            listener.remove()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Cart Page",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        )

        LazyColumn (
            modifier = Modifier.weight(1f)
        ) {
            items(userModel.value.cartItems.toList(), key = {it.first}) {
                (productId, qty) ->
                CartItemsView(productId = productId, qty = qty)

            }

        }
        Button(onClick =  {
                GlobalNavigation.navController.navigate("checkout")

        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Check out")
        }

    }
}