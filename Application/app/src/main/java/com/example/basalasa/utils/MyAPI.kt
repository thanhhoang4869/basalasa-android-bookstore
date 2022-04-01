package com.example.basalasa.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPI {
    companion object {
<<<<<<< HEAD
        private const val BASE_URL = "http://172.19.200.190"
=======

        private const val BASE_URL = "http://192.168.100.3"

>>>>>>> d05aa3bcf0c8681a0f3b9853e60eb281f487c712
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