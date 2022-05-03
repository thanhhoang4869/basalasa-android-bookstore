package com.example.basalasa.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.example.basalasa.databinding.ActivityBookDetailBinding
import com.example.basalasa.databinding.ActivitySellerBookDetailsBinding
import com.example.basalasa.model.body.GetDetailsBody
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.model.reponse.GetBookDetailResponse
import com.example.basalasa.model.reponse.SellerDeleteBookResponse
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

class SellerBookDetails : AppCompatActivity() {
    private lateinit var binding: ActivitySellerBookDetailsBinding
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBookDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        id = intent.getStringExtra("id").toString()
        loadDetail(id!!)
    }

    override fun onResume() {
        super.onResume()
        loadDetail(id)
    }

    private fun loadDetail(id: String) {
        val response = MyAPI.getAPI().getBookDetail(GetDetailsBody(id))
        response.enqueue(object : Callback<GetBookDetailResponse> {
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!

                    val formatter: NumberFormat = DecimalFormat("#,###")
                    binding.bookPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.bookTitle.text = data.name
                    Picasso.get().load(data.picture).into(binding.bookImage)
                    Picasso.get().load(data.picture).into(binding.animation)
                    binding.bookDescription.text =
                        HtmlCompat.fromHtml(data.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
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

                    binding.btnDelete.setOnClickListener {
                        deleteBook(data._id)
                    }

                    binding.btnEdit.setOnClickListener {
                        val intent= Intent(this@SellerBookDetails, SellerBookManagementUpdate::class.java)
                        intent.putExtra("id",data._id)
                        startActivity(intent)
                    }
                } else {
                    Log.i("test", "fail")
                }
            }

            override fun onFailure(call: Call<GetBookDetailResponse>, t: Throwable) {
                Toast.makeText(this@SellerBookDetails, "Fail connection to server", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace()
            }

        })
    }

    private fun deleteBook(id:String){
        val token = this@SellerBookDetails?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().sellerDeleteBook(it, id) }

        response!!.enqueue(object : Callback<SellerDeleteBookResponse> {
            override fun onResponse(call: Call<SellerDeleteBookResponse>, response: Response<SellerDeleteBookResponse>) {
                if (response.isSuccessful) {
                    val error=response.body()!!.error
                    if(error){
                        Toast.makeText(this@SellerBookDetails,"Fail!",Toast.LENGTH_SHORT).show()
                    }else{
                        val alertDialog: AlertDialog? = this.let {
                            val builder = AlertDialog.Builder(this@SellerBookDetails!!)
                            builder.apply {
                                setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, id ->
                                    finish()
                                })
                                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                                    //do sth
                                })
//                                setIcon(android.R.drawable.ic_dialog_alert)
                                setTitle("Do you want to delete this book?")
                            }
                            builder.create()
                        }
                        alertDialog!!.show()
                    }
                }
            }
            override fun onFailure(call: Call<SellerDeleteBookResponse>, t: Throwable) {
                Toast.makeText(this@SellerBookDetails, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}