package com.example.basalasa.model
import com.google.gson.annotations.SerializedName

class GetAccountResponse {
    @SerializedName("exitcode") val exitcode: Int=0
    @SerializedName("email") val email: String=""
    @SerializedName("name") val name: String=""
    @SerializedName("phone") val phone: String=""
    @SerializedName("address") val address: String=""
    @SerializedName("role") val role: Int=1
}