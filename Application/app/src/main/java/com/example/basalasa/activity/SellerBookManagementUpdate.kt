package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.basalasa.databinding.ActivitySellerBookManagementUpdateBinding
import com.example.basalasa.model.reponse.SellerDeleteBookResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerBookManagementUpdate : AppCompatActivity() {
    private lateinit var binding: ActivitySellerBookManagementUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBookManagementUpdateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id=intent.getStringExtra("id")
        loadBookInfo(id!!)
    }

    private fun loadBookInfo(id:String){
        val token = this.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().sellerUpdateBook(it, id) }

        response!!.enqueue(object : Callback<SellerDeleteBookResponse> {
            override fun onResponse(call: Call<SellerDeleteBookResponse>, response: Response<SellerDeleteBookResponse>) {
                if (response.isSuccessful) {
                    val error=response.body()!!.error
                    if(error){
                        Toast.makeText(this@SellerBookManagementUpdate,"Fail!", Toast.LENGTH_SHORT).show()
                    }else{

                    }
                }
            }
            override fun onFailure(call: Call<SellerDeleteBookResponse>, t: Throwable) {
                Toast.makeText(this@SellerBookManagementUpdate, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}