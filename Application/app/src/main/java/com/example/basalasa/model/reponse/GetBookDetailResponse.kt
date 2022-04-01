package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.BookDetail
import com.example.basalasa.model.entity.Comments
import com.google.gson.annotations.SerializedName

class GetBookDetailResponse {
    @SerializedName("id") val id:Int=0
    @SerializedName("name") val name:String=""
    @SerializedName("author") val author:String=""
    @SerializedName("distributor") val distributor:String=""
    @SerializedName("seller") val seller:String=""
    @SerializedName("price") val price:Double=0.0
    @SerializedName("saleprice") val saleprice:Double=0.0
    @SerializedName("category") val category:String=""
    @SerializedName("picture") val picture:String=""
    @SerializedName("release_year") val release_year:String=""
    @SerializedName("description") val description:String=""
    @SerializedName("quantity") val quantity:Int = 0
    @SerializedName("state") val state:Boolean = true
    @SerializedName("star") val star:Int =0
    @SerializedName("comments") val comments:ArrayList<Comments>?=null
}