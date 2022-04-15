package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class AddCartBody(
    @SerializedName("id")val id:Int,
    @SerializedName("quantity") val quantity: Int
)