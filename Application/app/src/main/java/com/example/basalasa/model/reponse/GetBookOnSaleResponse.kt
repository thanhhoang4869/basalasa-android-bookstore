package com.example.basalasa.model.reponse

import com.google.gson.annotations.SerializedName

class GetBookOnSaleResponse {
    @SerializedName("arrCategory") val arrBookOnSale: ArrayList<Book> = ArrayList()

}