package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class ChangeAccStateBody(
    @SerializedName("email") val email: String,
    @SerializedName("newState") val newState: Int
)