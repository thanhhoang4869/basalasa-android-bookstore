package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.google.gson.annotations.SerializedName

class GetFilterResultsResponse {
    @SerializedName("filterResults") lateinit var filterResults:ArrayList<Book>
}