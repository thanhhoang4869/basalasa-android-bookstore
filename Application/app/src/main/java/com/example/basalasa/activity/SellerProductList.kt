package com.example.basalasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.databinding.ActivitySellerProductListBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.reponse.GetBooksResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProductList : AppCompatActivity() {
    private lateinit var binding: ActivitySellerProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val user=intent.getStringExtra("user")
        loadListBook(user!!)
    }

    private fun loadListBook(user:String){
        val response = MyAPI.getAPI().getBooks()
        val arrBooks = arrayListOf<Book>()

        response.enqueue(object : Callback<GetBooksResponse> {
            override fun onResponse(call: Call<GetBooksResponse>, response: Response<GetBooksResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data!!.arrBook!!) {
                        if(item.seller==user)
                            arrBooks.add(item)
                    }

                    val adapter= CategoryAdapter(arrBooks)
                    binding.rvSellerProductList.adapter = adapter
                    binding.rvSellerProductList.layoutManager = GridLayoutManager(this@SellerProductList,2)
                    adapter.setOnItemClickListener(object : CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@SellerProductList,BookDetail::class.java)
                            intent.putExtra("id",arrBooks[position]._id)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetBooksResponse>, t: Throwable) {
                Toast.makeText(this@SellerProductList, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}