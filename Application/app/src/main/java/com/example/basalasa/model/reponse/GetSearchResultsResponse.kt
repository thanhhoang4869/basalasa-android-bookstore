package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Book
import com.google.gson.annotations.SerializedName

class GetSearchResultsResponse {
    @SerializedName("searchResults") lateinit var searchResults:ArrayList<Book>
}