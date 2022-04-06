package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName

class BooksInCart {
    @SerializedName("name") val name:String=""
    @SerializedName("price") val price:Int=0
    @SerializedName("img") val img:String=""
    @SerializedName("quantity")
    var quantity:Int=0
    override fun toString(): String {
        return name+"-"+price
    }
}