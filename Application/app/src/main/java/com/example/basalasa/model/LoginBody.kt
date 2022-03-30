package com.example.basalasa.model

import com.google.gson.annotations.SerializedName

class LoginBody(
    @SerializedName("email") val username: String,
    @SerializedName("password") val password: String
)
