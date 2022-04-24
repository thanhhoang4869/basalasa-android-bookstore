package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName

class CustomerBookHistory {
    @SerializedName("_id") val _id: String = ""
    @SerializedName("quantity") val quantity: Int = 0
    @SerializedName("name") val name: String = ""
    @SerializedName("picture") val picture: String = ""
    @SerializedName("price")val price:Int=0
    @Override
    override fun toString(): String {
        return _id+" "+" "+quantity+" "+name+" "+picture+" "+price
    }
}