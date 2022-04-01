package com.example.basalasa.model.entity

import com.google.gson.annotations.SerializedName

class Comments {
    @SerializedName("userEmail") val userEmail:String=""
    @SerializedName("rating") val rating:Int=0
    @SerializedName("review") val review:String=""
<<<<<<< HEAD
}
=======
    override fun toString(): String {
        return userEmail+"-"+review
    }
}
>>>>>>> d05aa3bcf0c8681a0f3b9853e60eb281f487c712
