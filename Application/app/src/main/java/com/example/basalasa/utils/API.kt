package com.example.basalasa.utils

import com.example.basalasa.activity.ForgetPassword
import com.example.basalasa.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/account/login")
    fun postLogin(
        @Body loginBody: LoginBody
    ): Call<LoginResponse>

    @POST("/account/register")
    fun postRegister(
        @Body registerBody: RegisterBody
    ): Call<RegisterResponse>

    @POST("/account/forget")
    fun postForgetPassword(
        @Body forgetBody: ForgetBody
    ): Call<ForgetResponse>
}