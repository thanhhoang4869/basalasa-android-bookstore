package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BooksInCart : Serializable {
    @SerializedName("id") val id:Int=0
    @SerializedName("name") val name:String=""
    @SerializedName("price") val price:Int=0
    @SerializedName("img") val img:String=""
    @SerializedName("quantity")var quantity:Int=0
    @SerializedName("seller") val seller :String=""

    override fun toString(): String {
        return name+"-"+price
    }
}