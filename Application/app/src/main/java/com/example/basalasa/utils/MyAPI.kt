package com.example.basalasa.utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPI {
    companion object {
        private const val BASE_URL = "http://172.20.10.2"
        private const val PORT = "3000"

        fun getAPI(): API {
            return Retrofit.Builder()
                .baseUrl("$BASE_URL:$PORT")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}