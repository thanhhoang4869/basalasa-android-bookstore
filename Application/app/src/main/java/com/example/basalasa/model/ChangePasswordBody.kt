package com.example.basalasa.model

import com.google.gson.annotations.SerializedName

class ChangePasswordBody (
    @SerializedName("oldPassword") val oldPassword: String,
    @SerializedName("newPassword") val newPassword: String,
    @SerializedName("rePassword") val rePassword: String
)