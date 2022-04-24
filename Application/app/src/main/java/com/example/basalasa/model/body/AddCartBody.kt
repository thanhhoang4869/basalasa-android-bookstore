package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class AddCartBody(
    @SerializedName("_id")val _id:String,
    @SerializedName("quantity") val quantity: Int
)