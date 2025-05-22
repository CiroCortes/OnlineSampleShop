package com.cirodevs.tiendaonlinefirebase.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cirodevs.tiendaonlinefirebase.AppUtil
import com.cirodevs.tiendaonlinefirebase.GlobalNavigation
import com.cirodevs.tiendaonlinefirebase.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CartItemsView( modifier: Modifier = Modifier,productId : String, qty : Long) {

    var product by remember {mutableStateOf(ProductModel())}


    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .document(productId).get().addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result.toObject(ProductModel::class.java)
                    if(result != null){
                        product = result
                    }

                }
            }

    }

    // detalle del producto
    var context = LocalContext.current

    Card  (
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)

    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Column( modifier = Modifier
                .padding(8.dp)
                .weight(1f)
            ) {
                Text(text = product.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,


                )
                Text(
                    text = "$" +product.actualPrice,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        AppUtil.removeFromCart(context, productId)
                    }) {
                        Text(text= "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = qty.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = {
                        AppUtil.addItemToCart(context, productId)
                    }) {
                        Text(text= "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

            }



            IconButton(onClick = {

                AppUtil.removeFromCart(context, productId, removeAll = true)

            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from cart"
                )
            }


        }
    }
}