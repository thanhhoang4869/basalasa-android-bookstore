package com.example.basalasa.model.body
import com.example.basalasa.model.entity.BooksInCart
import com.google.gson.annotations.SerializedName

class CheckoutBody(
    @SerializedName("arrBooks")val arrBooks:ArrayList<BooksInCart>,
    @SerializedName("email")val email:String="",
    @SerializedName("address")val address:String="",
    @SerializedName("phone")val phone:String=""
)