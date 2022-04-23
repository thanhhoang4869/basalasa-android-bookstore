package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class UpdateCartBody (
    @SerializedName("id")val id:Int,
    @SerializedName("quantity") val quantity: Int
)