package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.entity.Cart
import com.google.gson.annotations.SerializedName

class GetCartResponse {
    @SerializedName("email") val email:String=""
    @SerializedName("arrBooks") val arrBooks: ArrayList<BooksInCart>? = null
}