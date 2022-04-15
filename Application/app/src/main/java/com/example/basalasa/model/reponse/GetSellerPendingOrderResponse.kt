package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.CustomerHistory
import com.google.gson.annotations.SerializedName

class GetSellerPendingOrderResponse {
    @SerializedName("results") lateinit var results:ArrayList<CustomerHistory>
}