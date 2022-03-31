package com.example.basalasa.model.entity

class Category (
    val name: String,
    val image: String,
) {
    override fun toString(): String {
        return "${name},${image}"
    }
}