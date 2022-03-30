package com.example.basalasa.model.entity

class Account (
    val email: String,
    val name: String,
    val phone: String,
    val address: String,
    val role:Int,
) {
    override fun toString(): String {
        return "${email},${name},${phone},${address},${role}"
    }
}