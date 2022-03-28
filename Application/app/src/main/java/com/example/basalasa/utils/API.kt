package com.example.basalasa.utils

import com.example.basalasa.model.LoginBody
import com.example.basalasa.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/account/login")
    fun postLogin(
        @Body loginBody: LoginBody
    ): Call<LoginResponse>
}