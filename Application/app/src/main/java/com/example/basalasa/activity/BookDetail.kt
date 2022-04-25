package com.example.basalasa.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.basalasa.databinding.ActivityBookDetailBinding
import com.example.basalasa.model.body.AddCartBody
import com.example.basalasa.model.body.GetDetailsBody
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.model.reponse.GetUpdateResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class BookDetail : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.getStringExtra("id")
        loadDetail(id!!)
    }

    private fun loadDetail(id: String) {
        val response = MyAPI.getAPI().getBookDetail(GetDetailsBody(id))
        val token = Cache.getToken(this)
        val intent = Intent(this, Login::class.java)
        response.enqueue(object : Callback<GetBookDetailResponse> {
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!

                    val formatter: NumberFormat = DecimalFormat("#,###")
//                    binding.bookPrice.text=data.price.toString()
                    binding.bookPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.bookTitle.text = data.name
                    Picasso.get().load(data.picture).into(binding.bookImage)
                    binding.bookDescription.text =
                        HtmlCompat.fromHtml(data.description, HtmlCompat.FROM_HTML_MODE_COMPACT);
                    binding.bookAuthor.text = data.author

                    if (data.saleprice.toString() == "0") {
                        binding.bookSalePrice.text = formatter.format(data.price)
                    } else {
                        binding.bookPrice.text = formatter.format(data.price)
                        binding.bookSalePrice.text = formatter.format(data.saleprice)

                    }

                    val simpleDate = SimpleDateFormat("yyyy")
                    data.release_year = simpleDate.format(Date())

                    binding.bookRelease.text = data.release_year
                    binding.bookQuantity.text = data.quantity.toString()
                    binding.bookSeller.text = data.seller
                    binding.bookCate.text = data.category

                    binding.addCartBtn.setOnClickListener {
                        if (token === null) {
                            startActivity(intent)
                        } else {
                            val response_ = MyAPI.getAPI().getAccount(token.toString())

                            response_.enqueue(object : Callback<GetAccountResponse> {
                                override fun onResponse(
                                    call: Call<GetAccountResponse>,
                                    response: Response<GetAccountResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val data_ = response.body()
                                        Log.d("data", data_?.exitcode.toString())
                                        if (data_?.exitcode == 0) {
                                            addCart(token.toString(), data._id)
                                        }
                                    } else {
                                        startActivity(intent)
                                    }
                                }

                                override fun onFailure(
                                    call: Call<GetAccountResponse>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@BookDetail,
                                        "Fail connection to server",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    t.printStackTrace()
                                }
                            })
                        }
                    }
                } else {
                    Log.i("test", "fail")
                }
            }

            override fun onFailure(call: Call<GetBookDetailResponse>, t: Throwable) {
                Toast.makeText(this@BookDetail, "Fail connection to server", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace()
            }

        })
    }

    fun addCart(token: String, id: String) {
        val response1 = MyAPI.getAPI().addCart(token, AddCartBody(id, 1))
        response1.enqueue(object : Callback<GetUpdateResponse> {
            override fun onResponse(
                call: Call<GetUpdateResponse>,
                response: Response<GetUpdateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@BookDetail, "Add to cart successfully", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<GetUpdateResponse>, t: Throwable) {
                Toast.makeText(this@BookDetail, "Fail connection to server", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace()
            }


        })
    }
}