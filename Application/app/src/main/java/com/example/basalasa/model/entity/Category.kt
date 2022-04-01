package com.example.basalasa.model.entity

class Category (
    val name: String,
    val image: String,
) {
    constructor(item: Category) : this(
        item.name,
        item.image,
    )

    override fun toString(): String {
        return "${name},${image}"
    }
}