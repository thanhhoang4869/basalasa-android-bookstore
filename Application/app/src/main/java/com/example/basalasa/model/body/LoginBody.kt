package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class LoginBody(
    @SerializedName("email") val username: String,
    @SerializedName("password") val password: String
)
