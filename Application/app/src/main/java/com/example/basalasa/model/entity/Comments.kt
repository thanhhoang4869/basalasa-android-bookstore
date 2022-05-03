package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName

class Comments {
    @SerializedName("userEmail") val userEmail:String=""
    @SerializedName("rating") val rating:Double=0.0
    @SerializedName("review") val review:String=""

    override fun toString(): String {
        return userEmail+"-"+review
    }
}
