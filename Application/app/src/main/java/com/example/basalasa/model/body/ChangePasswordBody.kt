package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class ChangePasswordBody (
    @SerializedName("email") val email: String,
    @SerializedName("oldPassword") val oldPassword: String,
    @SerializedName("newPassword") val newPassword: String,
)