package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetAccountResponse

class Account(
    val email: String,
    val name: String,
    val phone: String,
    val address: String,
    val role: Int,
) {
    constructor(response: GetAccountResponse) : this(
        response.email,
        response.name,
        response.phone,
        response.address,
        response.role
    )

    override fun toString(): String {
        return "${email},${name},${phone},${address},${role}"
    }
}