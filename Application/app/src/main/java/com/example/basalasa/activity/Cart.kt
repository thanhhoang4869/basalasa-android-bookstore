package com.example.basalasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.adapter.CartAdapter
import com.example.basalasa.databinding.ActivityCartBinding
import com.example.basalasa.model.body.DeleteCartBody
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.GetCartResponse
import com.example.basalasa.model.reponse.GetUpdateResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Cart : AppCompatActivity() {
    private var binding: ActivityCartBinding? = null
    lateinit var arrBooks: ArrayList<BooksInCart>
    lateinit var adapter:CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        loadListCart()
        binding = ActivityCartBinding.inflate(layoutInflater)

    }
    private fun loadListCart(){
        val token = Cache.getToken(this)
        if (token === null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val response = MyAPI.getAPI().getCart(token.toString())
        arrBooks = ArrayList()
        var choosen:HashMap<Int,BooksInCart>  = HashMap<Int,BooksInCart>()
        var total=0
        var seller:String=""
        response.enqueue(object : Callback<GetCartResponse> {
            override fun onResponse(call: Call<GetCartResponse>, response: Response<GetCartResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    for(item: BooksInCart in data!!.arrBooks!!) {
                        arrBooks.add(item)
                    }
////
////                    //bind to adapter

                    val TotalView:TextView=findViewById(R.id.total)
                    TotalView.text=total.toString()
                    adapter= CartAdapter(arrBooks,TotalView)
                    val listCartitem:RecyclerView = findViewById(R.id.listCartitem)
                    listCartitem.adapter = adapter
                    listCartitem.layoutManager = LinearLayoutManager(this@Cart)
                    adapter.onItemClick={
                        s,position->Deletedata(s,position)
                        TotalView.text = (Integer.parseInt(TotalView.text.toString())-s.price).toString()
                    }
                    adapter.onCheckClick={
                        s,position,check_btn->
                        if(choosen.size==0){
                            System.out.println("ALO")
                            choosen.put(s.id,s)
                            seller=s.seller
                            TotalView.text =(Integer.parseInt(TotalView.text.toString())+s.price*s.quantity).toString()
                        }
                        else{
                            System.out.println(s.seller)
                            System.out.println(seller)
                            if(s.seller==seller){
                                choosen.put(s.id,s)
                                TotalView.text =(Integer.parseInt(TotalView.text.toString())+s.price*s.quantity).toString()
                            }
                            else{
                                check_btn.isChecked=false
                                Toast.makeText(this@Cart, "Different Seller, choose again", Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                    adapter.removeCheck={
                            s,position->
                        if(choosen.get(s.id)?.seller==seller){
                            System.out.println(choosen.get(s.id)?.seller)
                            System.out.println(seller)
                            choosen.remove(s.id)
                            if(choosen.size==0)
                                seller=""
                            TotalView.text =(Integer.parseInt(TotalView.text.toString())-s.price*s.quantity).toString()
                        }
                        Toast.makeText(this@Cart, "Dit me may", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<GetCartResponse>, t: Throwable) {
                Toast.makeText(this@Cart, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }


        })

    }
    fun Deletedata(book:BooksInCart,position:Int){
        val token = Cache.getToken(this)
        val response = MyAPI.getAPI().deleteCart(token.toString(), DeleteCartBody(book.name))
        response.enqueue(object : Callback<GetUpdateResponse> {
            override fun onResponse(call: Call<GetUpdateResponse>, response: Response<GetUpdateResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Cart, "Delete Successfully", Toast.LENGTH_LONG).show()
                    arrBooks.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }

            }

            override fun onFailure(call: Call<GetUpdateResponse>, t: Throwable) {
                Toast.makeText(this@Cart, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

}