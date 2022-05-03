package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class CommentBody (
    @SerializedName("_id")val _id:String="",
    @SerializedName("rating") val rating:Double=0.0,
    @SerializedName("review")val review:String="",
    @SerializedName("orderID") val orderID:String=""
        )