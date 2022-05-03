package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class UpdateBookBody(
    val id:String,
    val author:String,
    val description:String,
    val distributor:String,
    val saleprice:Int=0,
    val quantity:Int=0,
    val price:Int=0
) {

}