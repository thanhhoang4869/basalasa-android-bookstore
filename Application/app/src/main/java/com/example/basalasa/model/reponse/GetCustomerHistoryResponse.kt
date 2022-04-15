package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.CustomerHistory
import com.google.gson.annotations.SerializedName

class GetCustomerHistoryResponse {
    @SerializedName("orders") val arrHistory: ArrayList<CustomerHistory>? = null
}