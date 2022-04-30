package com.example.basalasa.utils

import com.example.basalasa.model.body.*
import com.example.basalasa.model.reponse.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface API {
    //user
    @POST("/account/login")
    fun postLogin(
        @Body loginBody: LoginBody
    ): Call<LoginResponse>

    @POST("/account/request")
    fun postSendRequest(
        @Header("x-access-token") tokenHeader: String,
    ): Call<SendRequestResponse>

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

    @POST("account/history/confirm")
    fun confirmOrder(
        @Header("x-access-token") tokenHeader: String,
        @Body cancelOrderBody: CancelOrderBody
    ): Call<CancelOrderResponse>
    @POST("account/history/deliver")
    fun deliverOrder(
        @Header("x-access-token") tokenHeader: String,
        @Body cancelOrderBody: CancelOrderBody
    ): Call<CancelOrderResponse>
    @POST("account/history/done")
    fun doneOrder(
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
    fun getBooks(): Call<GetBooksResponse>

    @POST("/book/getDetails")
    fun getBookDetail(
        @Body getDetailsBody: GetDetailsBody
    ): Call<GetBookDetailResponse>

    //search
    @POST("/search")
    fun getSearchResults(
        @Body getSearchResultsBody: SearchResultsBody
    ): Call<GetSearchResultsResponse>

    @POST("/search/filter")
    fun getFilterResults(
        @Body getFilterResultsBody: FilterResultsBody
    ): Call<GetFilterResultsResponse>

    @GET("/cart")
    fun getCart(
        @Header("x-access-token") tokenHeader: String,
    ): Call<GetCartResponse>

    @POST("/cart/update")
    fun updateCart(
        @Header("x-access-token") tokenHeader: String,
        @Body getUpdateResultsBody: UpdateCartBody
    ): Call<GetUpdateResponse>

    @POST("/cart/delete")
    fun deleteCart(
        @Header("x-access-token") tokenHeader: String,
        @Body deleteCartBody: DeleteCartBody
    ): Call<GetUpdateResponse>

    @POST("/cart/add")
    fun addCart(
        @Header("x-access-token") tokenHeader: String,
        @Body getAddCartBodyResult: AddCartBody
    ): Call<GetUpdateResponse>

    //seller: order list
    @GET("/seller/pending")
    fun getSellerPendingOrder(
        @Header("x-access-token") tokenHeader: String,
        @Query("seller") seller: String
    ): Call<GetSellerPendingOrderResponse>

    @GET("/seller/processing")
    fun getSellerProcessingOrder(
        @Header("x-access-token") tokenHeader: String,
        @Query("seller") seller: String
    ): Call<GetSellerPendingOrderResponse>

    @GET("/seller/completed")
    fun getSellerCompletedOrder(
        @Header("x-access-token") tokenHeader: String,
        @Query("seller") seller: String
    ): Call<GetSellerPendingOrderResponse>

    @GET("/seller/completed")
    fun getSellerCanceledOrder(
        @Header("x-access-token") tokenHeader: String,
        @Query("seller") seller: String
    ): Call<GetSellerPendingOrderResponse>

    //seller: book management
    @GET("/seller/delete")
    fun sellerDeleteBook(
        @Header("x-access-token") tokenHeader: String,
        @Query("id") id: String
    ):Call<SellerDeleteBookResponse>

    @GET("/seller/update")
    fun sellerUpdateBook(
        @Header("x-access-token") tokenHeader: String,
        @Query("id") id: String
    ):Call<SellerDeleteBookResponse>

    //admin
    @GET("/admin/getAccountList")
    fun getAccountList(
        @Header("x-access-token") tokenHeader: String
    ): Call<GetAccountListResponse>

    @GET("/admin/getRequestList")
    fun getRequestList(
        @Header("x-access-token") tokenHeader: String
    ): Call<GetRequestListResponse>

    @POST("/admin/changeAccState")
    fun postChangeAccState(
        @Header("x-access-token") tokenHeader: String,
        @Body changeAccStateBody: ChangeAccStateBody
    ): Call<ChangeAccStateResponse>

    @POST("/admin/changeRole")
    fun postChangeRole(
        @Header("x-access-token") tokenHeader: String,
        @Body changeRoleBody: ChangeRoleBody
    ): Call<ChangeRoleResponse>

    @POST("/order/add")
    fun postItemCheckout(
        @Header("x-access-token") tokenHeader: String,
        @Body CheckoutBody:CheckoutBody
    ):Call<CheckoutResponse>

    @Multipart
    @POST("/book/add")
    fun addBook(
        @Header("x-access-token") tokenHeader: String,
        @Part("name")  name: RequestBody,
        @Part("author")  author: RequestBody,
        @Part("distributor")  distributor: RequestBody,
        @Part("seller")  seller: RequestBody,
        @Part("price")  price: RequestBody,
        @Part("category")   category: RequestBody,
        @Part("release_year")  release_year: RequestBody,
        @Part("description")  description: RequestBody,
        @Part("quantity")  quantity: RequestBody,
        @Part("state")  state: RequestBody,
        @Part("star")  star: RequestBody,
        @Part img:MultipartBody.Part,
        ):Call<AddBookResponse>

}