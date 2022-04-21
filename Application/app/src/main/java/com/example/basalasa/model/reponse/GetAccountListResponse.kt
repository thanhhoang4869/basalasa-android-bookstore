package com.example.basalasa.model.reponse

import com.example.basalasa.model.entity.Account
import com.google.gson.annotations.SerializedName

class GetAccountListResponse {
    @SerializedName("exitcode") val exitcode: Int=0
    @SerializedName("accList") val accList: ArrayList<Account> = ArrayList()
}