package com.example.basalasa.model.entity

import java.util.*
import kotlin.collections.ArrayList

class Book (
    val id: Int,
    val name: String,
    val author: String,
    val distributor: String,
    val seller: String,
    val price: Int,
    val salePrice: Int,
    val category: String,
    val picture: String,
    val releaseDay: Date,
    val description: String,
    val quantity: Int,
    val state: Int,
    val star: Int,
    var comments: ArrayList<Comments>? = ArrayList()
) {

}