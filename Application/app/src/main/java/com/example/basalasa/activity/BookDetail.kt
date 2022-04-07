package com.example.basalasa.activity

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.adapter.CartAdapter
import com.example.basalasa.databinding.ActivityBookDetailBinding
import com.example.basalasa.model.body.AddCartBody
import com.example.basalasa.model.body.GetDetailsBody
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.model.reponse.GetCartResponse
import com.example.basalasa.model.reponse.GetUpdateResponse
import com.example.basalasa.utils.Cache
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
        val token = Cache.getToken(this)
        val intent = Intent(this, Login::class.java)
        response.enqueue(object : Callback<GetBookDetailResponse>{
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                if (response.isSuccessful){
                    val data=response.body()!!

                    binding.bookPrice.text=data.price.toString()
                    binding.bookPrice.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
                    binding.bookTitle.text=data.name
                    Picasso.get().load(data.picture).into(binding.bookImage)
                    binding.bookDescription.text=data.description
                    binding.bookAuthor.text=data.author
                    binding.bookSalePrice.text=data.saleprice.toString()
                    binding.bookRelease.text=data.release_year
                    binding.bookQuantity.text=data.quantity.toString()
                    binding.bookSeller.text=data.seller
                    binding.bookCate.text=data.category

                    binding.addCartBtn.setOnClickListener {
                        if (token === null) {
                            startActivity(intent)
                            finish()
                        }
                        addCart(token.toString(),data.name,data.saleprice,data.picture)
                    }
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
    fun addCart(token:String,name:String,price:Int,img:String){
        val response1 = MyAPI.getAPI().addCart(token, AddCartBody(name,price,img,1))
        response1.enqueue(object : Callback<GetUpdateResponse> {
            override fun onResponse(call: Call<GetUpdateResponse>, response: Response<GetUpdateResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@BookDetail, "Add to cart successfully", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<GetUpdateResponse>, t: Throwable) {
                Toast.makeText(this@BookDetail, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }


        })
    }
}