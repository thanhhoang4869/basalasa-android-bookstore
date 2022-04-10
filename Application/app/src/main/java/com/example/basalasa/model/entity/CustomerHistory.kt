package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.model.reponse.GetCustomerHistoryResponse

class CustomerHistory(
    val email: String,
    val arrProduct: ArrayList<CustomerBookHistory>?,
    val status: String,
    val date: String,
    val total: Int
) {
}