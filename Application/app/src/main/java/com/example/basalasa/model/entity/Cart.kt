package com.example.basalasa.model.entity

import com.example.basalasa.model.reponse.GetCartResponse

class Cart(
    val email:String,
    val arrBooks:ArrayList<BooksInCart>?=null
) {
    constructor(response: GetCartResponse) : this(
        response.email,
        response.arrBooks
    )
}