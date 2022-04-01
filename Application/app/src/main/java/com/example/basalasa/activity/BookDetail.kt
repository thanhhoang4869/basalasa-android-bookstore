package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.basalasa.R
import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val id=intent.getStringExtra("id")
        loadDetail("123")
    }

    private fun loadDetail(id: String){
        val response = MyAPI.getAPI().getBookDetail(id)

        response.enqueue(object : Callback<GetBookDetailResponse>{
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                if (response.isSuccessful){
                    val data=response.body()
                    Log.i("?",data.toString())
                }else{
                    Log.i("?","fail")
                }
            }

            override fun onFailure(call: Call<GetBookDetailResponse>, t: Throwable) {
                Toast.makeText(this@BookDetail, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

        })
    }
}