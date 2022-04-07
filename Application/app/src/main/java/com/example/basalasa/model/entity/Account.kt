package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetAccountResponse

class Account(
    val email: String,
    val fullName: String,
    val phone: String,
    val address: String,
    val role: Int,
) {
    constructor(response: GetAccountResponse) : this(
        response.email,
        response.fullName,
        response.phone,
        response.address,
        response.role
    )

    override fun toString(): String {
        return "${email},${fullName},${phone},${address},${role}"
    }
}