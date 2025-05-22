package com.cirodevs.tiendaonlinefirebase.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cirodevs.tiendaonlinefirebase.AppUtil
import com.cirodevs.tiendaonlinefirebase.GlobalNavigation
import com.cirodevs.tiendaonlinefirebase.model.ProductModel
import com.cirodevs.tiendaonlinefirebase.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.nio.file.WatchEvent

@Composable
fun CheckOutPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    val productList = remember {
        mutableStateListOf(ProductModel())
    }

    val subTotal = remember {
        mutableStateOf(0f)
    }

    val discount = remember {
        mutableStateOf(0f)

    }
    val tax = remember {
        mutableStateOf(0f)

    }

    val total = remember {
        mutableStateOf(0f)

    }

    fun calculateAndAssign(){
        productList.forEach {
            if(it.actualPrice.isNotEmpty()){
                val qty = userModel.value.cartItems[it.id] ?: 0
                subTotal.value += it.actualPrice.toFloat() * qty
            }
        }

        discount.value = subTotal.value * (AppUtil.getDiscountPercentage())/100  // aplicacion de % descuentos

        tax.value = subTotal.value * (AppUtil.getTaxPercentage())/100  // aplicacion de % impuestos)

        total.value = subTotal.value - discount.value + tax.value

    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                        Firebase.firestore.collection("data").document("stock")
                            .collection("products")
                            .whereIn("id", userModel.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                val resultProduct = task.result.toObjects(ProductModel::class.java)
                                   productList.addAll(resultProduct)
                                    calculateAndAssign()
                                }
                            }
                    }
                }
            }

    }

    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxSize()

    ) {
        Text(text = "CheckOut", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Deliver to",fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.name)
        Text(text = userModel.value.address)
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        RowCheckOutItems(title = "SubTotal", value = subTotal.value.toString())
        Spacer(modifier = Modifier.height(8.dp))
        RowCheckOutItems(title = "Discount (-)", value = discount.value.toString())
        Spacer(modifier = Modifier.height(8.dp))
        RowCheckOutItems(title = "Tax (+)", value = tax.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "To Pay",
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$ " + total.value.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick =  {
            /*TODO*/

        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Proceeded to Payment")
        }

    }



}

@Composable
fun RowCheckOutItems(
    title : String, value : String
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ){
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "$ " + value, fontSize = 18.sp)

    }
}