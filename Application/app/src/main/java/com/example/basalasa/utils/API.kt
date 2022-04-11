package com.example.basalasa.utils

import com.example.basalasa.model.body.*
import com.example.basalasa.model.reponse.*
import retrofit2.Call
import retrofit2.http.*

interface API {
    //user
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

    @GET("/account/getAccount")
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

    //customer order
    @POST("/account/history")
    fun getHistory(
        @Header("x-access-token") tokenHeader: String,
        @Body getHistoryBody: GetHistoryBody
    ): Call<GetCustomerHistoryResponse>

    @POST("account/history/delete")
    fun cancelOrder(
        @Header("x-access-token") tokenHeader: String,
        @Body cancelOrderBody: CancelOrderBody
    ): Call<CancelOrderResponse>

    //category
    @GET("/category")
    fun getCategory(): Call<GetCategoryResponse>

    //book
    @GET("/book/onsale")
    fun getBookOnSale(): Call<GetBookOnSaleResponse>

    @GET("/book")
    fun getBooks():Call<GetBooksResponse>
    @POST("/book/getDetails")
    fun getBookDetail(
        @Body getDetailsBody: GetDetailsBody
    ):Call<GetBookDetailResponse>

    //search
    @POST("/search")
    fun getSearchResults(
        @Body getSearchResultsBody: SearchResultsBody
    ):Call<GetSearchResultsResponse>

    @POST ("/search/filter")
    fun getFilterResults(
        @Body getFilterResultsBody: FilterResultsBody
    ):Call<GetFilterResultsResponse>

    @GET("/cart")
    fun getCart(
        @Header("x-access-token") tokenHeader: String,
    ):Call<GetCartResponse>

    @POST("/cart/update")
    fun updateCart(
        @Header("x-access-token")tokenHeader: String,
        @Body getUpdateResultsBody:UpdateCartBody
    ):Call<GetUpdateResponse>

    @POST("/cart/delete")
    fun deleteCart(
        @Header("x-access-token")tokenHeader: String,
        @Body deleteCartBody:DeleteCartBody
    ):Call<GetUpdateResponse>

    @POST("/cart/add")
    fun addCart(
        @Header("x-access-token")tokenHeader: String,
        @Body getAddCartBodyResult:AddCartBody
    ):Call<GetUpdateResponse>
}