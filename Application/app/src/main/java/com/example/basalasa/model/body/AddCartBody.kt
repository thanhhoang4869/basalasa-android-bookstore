package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class AddCartBody(
    @SerializedName("name") val name:String,
    @SerializedName("price") val price:Int,
    @SerializedName("img") val img:String,
    @SerializedName("quantity") val quantity: Int
)