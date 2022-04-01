package com.example.basalasa.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPI {
    companion object {
<<<<<<< HEAD
        private const val BASE_URL = "http://10.126.5.204"
=======
        private const val BASE_URL = "http://192.168.100.3"
>>>>>>> d8cae7ce2a6ad0bbfba71092a15e3d8966131df4
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