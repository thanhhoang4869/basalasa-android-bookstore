package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName

class CustomerBookHistory {
    @SerializedName("book_id") val book_id: String = ""
    @SerializedName("quantity") val quantity: Int = 0
    @SerializedName("name") val name: String = ""
}