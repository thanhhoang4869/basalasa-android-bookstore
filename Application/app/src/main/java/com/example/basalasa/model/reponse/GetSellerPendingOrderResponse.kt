package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.SellerOrderBook
import com.example.basalasa.model.entity.SellerPendingOrder
import com.google.gson.annotations.SerializedName

class GetSellerPendingOrderResponse {
    @SerializedName("orders") lateinit var orders:ArrayList<SellerPendingOrder>
}