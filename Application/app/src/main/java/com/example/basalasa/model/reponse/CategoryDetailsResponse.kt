package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.google.gson.annotations.SerializedName

class CategoryDetailsResponse {
    @SerializedName("books") private lateinit var books:ArrayList<Book>
}