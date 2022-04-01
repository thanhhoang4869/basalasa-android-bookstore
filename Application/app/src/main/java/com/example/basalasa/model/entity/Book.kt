package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetAccountResponse
import java.util.*

class Book (
    val id: Int,
    val name: String,
    val author: String,
    val distributor: String,
    val seller: String,
    val price: Int,
    val salePrice: Int,
    val category: String,
    val picture: String,
    val releaseDay: Date,
    val description: String,
    val quantity: Int,
    val state: Boolean,
    val star: Int
) {
//    constructor(response: GetAccountResponse) : this(
//        response.email,
//        response.name,
//        response.phone,
//        response.address,
//        response.role
//    )
//
//    override fun toString(): String {
//        return "${email},${name},${phone},${address},${role}"
//    }
}