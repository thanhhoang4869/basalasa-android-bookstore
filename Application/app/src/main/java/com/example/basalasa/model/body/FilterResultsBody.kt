package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class FilterResultsBody(
    @SerializedName("category") val category: ArrayList<String>,
    @SerializedName("min") val min:String,
    @SerializedName("max") val max:String
) {
}