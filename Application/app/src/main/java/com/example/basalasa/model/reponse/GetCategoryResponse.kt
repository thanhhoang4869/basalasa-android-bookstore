package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Category
import com.google.gson.annotations.SerializedName

class GetCategoryResponse {
    @SerializedName("arrCategory") val arrCategory: ArrayList<Category>? = null
}