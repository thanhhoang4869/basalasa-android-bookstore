package com.example.basalasa.model.entity

import com.example.basalasa.model.GetCategoryResponse

class Category (
    val name: String,
    val image: String,
) {
    override fun toString(): String {
        return "${name},${image}"
    }
}