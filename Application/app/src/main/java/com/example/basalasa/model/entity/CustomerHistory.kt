package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.model.reponse.GetCustomerHistoryResponse

class CustomerHistory(
    val _id: String,
    val email: String,
    val product: ArrayList<CustomerBookHistory>?,
    val date: String,
    val status: String,
    val total: Int
) {
}