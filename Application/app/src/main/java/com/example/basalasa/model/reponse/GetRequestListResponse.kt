package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Account
import com.google.gson.annotations.SerializedName

class GetRequestListResponse {
    @SerializedName("exitcode") val exitcode: Int=0
    @SerializedName("reqList") val reqList: ArrayList<Account> = ArrayList()
}