package com.example.basalasa.utils

import com.example.basalasa.model.LoginResponse
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("/account/login")
    fun getLogin(
    ): Call<LoginResponse>

}