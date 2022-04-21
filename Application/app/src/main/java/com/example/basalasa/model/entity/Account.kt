package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetAccountResponse

class Account(
    var email: String,
    var fullName: String,
    var phone: String,
    var address: String,
    var role: Int,
    var status: Int,
    var request: Int
) {
    constructor():this(
        "",
        "",
        "",
        "",
        1,
        1,
        0
    )
    constructor(response: GetAccountResponse) : this(
        response.email,
        response.fullName,
        response.phone,
        response.address,
        response.role,
        response.status,
        response.request
    )

    override fun toString(): String {
        return "${email},${fullName},${phone},${address},${role},${status}"
    }
}