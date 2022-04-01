package com.example.basalasa.utils

import com.example.basalasa.model.body.*
import com.example.basalasa.model.reponse.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("/account/getAccount")
    fun getAccount(
        @Header("x-access-token") tokenHeader: String
    ): Call<GetAccountResponse>

    @POST("/account/changeInfo")
    fun changeInfo(
        @Header("x-access-token") tokenHeader: String,
        @Body changeInformationBody: ChangeInformationBody
    ): Call<ChangeInformationResponse>

    @POST("/account/changePass")
    fun changePass(
        @Header("x-access-token") tokenHeader: String,
        @Body changePasswordBody: ChangePasswordBody
    ): Call<ChangePasswordResponse>

    //category
    @GET("/category")
    fun getCategory(): Call<GetCategoryResponse>

    //book
    @GET("/book/onsale")
    fun getBookOnSale(): Call<GetBookOnSaleResponse>
}