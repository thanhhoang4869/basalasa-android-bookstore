package com.example.basalasa.utils

import com.example.basalasa.model.LoginBody
import com.example.basalasa.model.LoginResponse
import com.example.basalasa.model.RegisterBody
import com.example.basalasa.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/account/login")
    fun postLogin(
        @Body loginBody: LoginBody
    ): Call<LoginResponse>

    fun postRegister(
        @Body registerBody: RegisterBody
    ): Call<RegisterResponse>
}