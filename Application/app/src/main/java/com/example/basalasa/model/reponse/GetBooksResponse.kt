package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.google.gson.annotations.SerializedName

class GetBooksResponse {
    @SerializedName("arrBook") val arrBook: ArrayList<Book>? = null
}