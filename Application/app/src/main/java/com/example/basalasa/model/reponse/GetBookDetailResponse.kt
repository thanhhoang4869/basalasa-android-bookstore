package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.Comments
import com.google.gson.annotations.SerializedName

class GetBookDetailResponse {
    @SerializedName("_id")
    val _id: String = ""
    @SerializedName("id")
    val id: String = ""
    @SerializedName("name")
    val name: String = ""
    @SerializedName("author")
    val author: String = ""
    @SerializedName("distributor")
    val distributor: String = ""
    @SerializedName("seller")
    val seller: String = ""
    @SerializedName("price")
    val price: Int = 0
    @SerializedName("saleprice")
    val saleprice: Int = 0
    @SerializedName("category")
    val category: String = ""
    @SerializedName("picture")
    val picture: String = ""
    @SerializedName("release_year")
    var release_year: String = ""
    @SerializedName("description")
    val description: String = ""
    @SerializedName("quantity")
    val quantity: Int = 0
    @SerializedName("state")
    val state: Int = 1
    @SerializedName("star")
    val star: Int = 0
    @SerializedName("comments")
    val comments: ArrayList<Comments>? = null
    @SerializedName ("relatedBooks")
    val relatedBooks: ArrayList<Book>?=null
}