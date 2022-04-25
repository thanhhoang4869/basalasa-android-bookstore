package com.example.basalasa.model.entity

class SellerPendingOrder(
    val _id:String,
    val email:String,
    val product:ArrayList<SellerOrderBook>,
    val status:String,
    val date: String,
    val total:Int,
    val phone:String,
    val address:String,
    val receiver:String
) {
}