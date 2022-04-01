package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.google.gson.annotations.SerializedName

class GetBookOnSaleResponse {
    @SerializedName("arrCategory") val arrBookOnSale: ArrayList<Book> = ArrayList()

}