package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class CancelOrderBody (
    @SerializedName("orderId") val orderId: String,
)