package com.example.basalasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.adapter.CartAdapter
import com.example.basalasa.databinding.ActivityCartBinding
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.GetCartResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Cart : AppCompatActivity() {
//    private var _binding: ActivityCartBinding? = null
    lateinit var arrBooks: ArrayList<BooksInCart>
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        loadListCart()

    }
    fun loadListCart(){
        val token = Cache.getToken(this)
        if (token === null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val response = MyAPI.getAPI().getCart(token.toString())
        arrBooks = ArrayList()

        response.enqueue(object : Callback<GetCartResponse> {
            override fun onResponse(call: Call<GetCartResponse>, response: Response<GetCartResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    for(item: BooksInCart in data!!.arrBooks!!) {
                        arrBooks.add(item)
                        System.out.println("TEST"+item)
                    }
////
////                    //bind to adapter
                    val adapter= CartAdapter(arrBooks)
                    var listCartitem:RecyclerView = findViewById<RecyclerView>(R.id.listCartitem)
                    listCartitem.adapter = adapter
                    listCartitem.layoutManager = LinearLayoutManager(this@Cart)
                }
            }
            override fun onFailure(call: Call<GetCartResponse>, t: Throwable) {
                Toast.makeText(this@Cart, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }


        })

    }

}