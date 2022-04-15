package com.example.basalasa.model.reponse
import com.google.gson.annotations.SerializedName

class GetAccountResponse {
    @SerializedName("exitcode") val exitcode: Int=0
    @SerializedName("email") val email: String=""
    @SerializedName("fullName") val fullName: String=""
    @SerializedName("phone") val phone: String=""
    @SerializedName("address") val address: String=""
    @SerializedName("role") val role: Int=1
    @SerializedName("status") val status: Int=1
}