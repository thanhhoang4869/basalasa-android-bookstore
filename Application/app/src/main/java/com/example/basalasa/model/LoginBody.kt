package com.example.basalasa.model

import com.google.gson.annotations.SerializedName

class LoginBody(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
