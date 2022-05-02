package com.example.basalasa.model.body

import com.google.gson.annotations.SerializedName

class UpdateBookBody(
    var id:String,
    var author:String,
   var description:String,
  var distributor:String,
  val saleprice:Int=0,
  val quantity:Int=0,
) {

}