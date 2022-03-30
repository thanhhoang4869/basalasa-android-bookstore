package com.example.basalasa.model

import com.google.gson.annotations.SerializedName

class ChangeInformationBody (
    @SerializedName("fullName") val fullName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String
)