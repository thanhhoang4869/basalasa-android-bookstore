package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetBookDetailResponse
import kotlin.collections.ArrayList

class Book (
    val id: Int,
    val name: String,
    val author: String,
    val distributor: String,
    val seller: String,
    val price: Int,
    val saleprice: Int,
    val category: String,
    val picture: String,
    val release_year: String,
    val description: String,
    val quantity: Int,
    val state: Int,
    val star: Int,
    var comments:ArrayList<Comments>?=null
) {
    constructor(response: GetBookDetailResponse) : this(
        response.id,
        response.name,
        response.author,
        response.distributor,
        response.seller,
        response.price,
        response.saleprice,
        response.category,
        response.picture,
        response.release_year,
        response.description,
        response.quantity,
        response.state,
        response.star,
        response.comments
    )
    constructor(item:Book) : this(
        item.id,
        item.name,
        item.author,
        item.distributor,
        item.seller,
        item.price,
        item.saleprice,
        item.category,
        item.picture,
        item.release_year,
        item.description,
        item.quantity,
        item.state,
        item.star,
        item.comments)
    override fun toString(): String {
        return "${name},${price}"
    }
}