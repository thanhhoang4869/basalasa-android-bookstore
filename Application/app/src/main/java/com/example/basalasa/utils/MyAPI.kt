package com.example.basalasa.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPI {
    companion object {

<<<<<<< HEAD
        private const val BASE_URL = "http://192.168.11.107"
=======
        private const val BASE_URL = "http://192.168.100.3"
>>>>>>> 0c9f096fcc799a8751bdaab48ee1a91dac3b3e12

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