package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.basalasa.databinding.ActivitySellerBookManagementUpdateBinding
import com.example.basalasa.model.body.UpdateBookBody
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

        val id=intent.getStringExtra("id")!!

        binding.addBook.setOnClickListener {
            val author=binding.etAuthor.text.toString()
            val des=binding.etDescription.text.toString()
            val dis=binding.etDistributor.text.toString()
            val price=binding.etPrice.text.toString()
            val quan=binding.etQuantity.text.toString()

            if(author==""||des==""||title==""||dis==""||price==""||quan==""){
                Toast.makeText(this,"Please enter all the fields!",Toast.LENGTH_SHORT).show()
            }
            else{
                val token=Cache.getToken(this)
                val body=UpdateBookBody(id,author,des,dis,price.toInt(),quan.toInt())
                val response=MyAPI.getAPI().sellerUpdateBook(token!!,body)

                response.enqueue(object :Callback<SellerDeleteBookResponse>{
                    override fun onResponse(
                        call: Call<SellerDeleteBookResponse>,
                        response: Response<SellerDeleteBookResponse>
                    ) {
                        if(response.isSuccessful){
                            val data=response.body()!!
                            if(data.error){
                                Toast.makeText(this@SellerBookManagementUpdate,"Fail!",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@SellerBookManagementUpdate,"Success!",Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SellerDeleteBookResponse>, t: Throwable) {
                    }
                })
            }
        }
    }
}