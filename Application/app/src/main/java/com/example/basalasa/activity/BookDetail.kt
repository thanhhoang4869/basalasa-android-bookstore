package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.basalasa.databinding.ActivityBookDetailBinding
import com.example.basalasa.model.body.GetDetailsBody
import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.utils.MyAPI
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookDetail : AppCompatActivity() {
    private lateinit var binding:ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id=intent.getStringExtra("id")
        loadDetail(id!!)
    }

    private fun loadDetail(id: String){
        val response = MyAPI.getAPI().getBookDetail(GetDetailsBody(id))

        response.enqueue(object : Callback<GetBookDetailResponse>{
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                if (response.isSuccessful){
                    val data=response.body()!!

                    binding.bookPrice.text=data.price.toString()
                    binding.bookTitle.text=data.name
                    Picasso.get().load(data.picture).into(binding.bookImage)
                    binding.bookDescription.text=data.description
                    binding.bookAuthor.text=data.author
                    binding.bookSalePrice.text=data.saleprice.toString()
                    binding.bookRelease.text=data.release_year
                    binding.bookQuantity.text=data.quantity.toString()
                    if(data.state==0){
                        binding.bookState.text= "Out of stock"
                    }

                    binding.bookSeller.text=data.seller
                    binding.bookCate.text=data.category
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