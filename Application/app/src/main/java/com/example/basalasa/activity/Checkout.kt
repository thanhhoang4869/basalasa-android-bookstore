package com.example.basalasa.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.adapter.CheckoutAdapter
import com.example.basalasa.databinding.ActivityCheckoutBinding
import com.example.basalasa.model.body.CheckoutBody
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.CheckoutResponse
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Checkout : AppCompatActivity() {
    lateinit private var binding: ActivityCheckoutBinding
    lateinit private var hashMap:HashMap<String, BooksInCart>
    lateinit var adapter: CheckoutAdapter
    private var account: Account =Account()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var intent: Intent = getIntent();
        hashMap = intent.getSerializableExtra("map") as (HashMap<String, BooksInCart>)
        getInfo(this)
        Log.d("account2",account.toString())

        loadList()
    }
    fun loadList(){
        val token = Cache.getToken(this)
        if (token === null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val response = MyAPI.getAPI().getCart(token.toString())
        adapter = CheckoutAdapter(hashMap)
        binding.list.adapter=adapter
        binding.list.layoutManager = LinearLayoutManager(this@Checkout)
        var total:Int =0
        var arrBook:ArrayList<BooksInCart> = ArrayList()
        for(i in 0..hashMap.size-1){
            val keyByIndex = hashMap.keys.elementAt(i) // Get key by index.
            val valueOfElement = hashMap.getValue(keyByIndex)
            total += valueOfElement.price*valueOfElement.quantity
            arrBook.add(valueOfElement)
        }
        binding.shippingCost.text = "30000"
        binding.itemsCost.text= total.toString()
        binding.totalCost.text =(total +30000).toString()
        binding.submit.setOnClickListener {
            var email = binding.emailReceiver.text.toString()
            var phone= binding.phoneReceiver.text.toString()
            var address= binding.addressReceiver.text.toString()

            val response = MyAPI.getAPI().postItemCheckout(token.toString(),
                CheckoutBody(arrBook,email,address,phone)
            )
            response.enqueue(object : Callback<CheckoutResponse> {
                override fun onResponse(call: Call<CheckoutResponse>, response: Response<CheckoutResponse>) {
                    if (response.isSuccessful) {
                        println("SUCCESS")
                        val intent2= Intent(this@Checkout, Cart::class.java)
                        startActivity(intent2)
                        finish()
                    }
                }

                override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                    Toast.makeText(this@Checkout, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }

            })
        }
    }
    private fun getInfo(context: Context) {
        val token = Cache.getToken(context)
        val response = MyAPI.getAPI().getAccount(token.toString())

        response.enqueue(object : Callback<GetAccountResponse> {
            override fun onResponse(
                call: Call<GetAccountResponse>,
                response: Response<GetAccountResponse>
            ) {

                if (response.isSuccessful) {
                    val data = response.body()

                    if (data!!.exitcode == 0) {
                        account=Account(data!!)
                        Log.d("account",account.toString())
                        binding.emailReceiver.setText(account.email)
                        binding.addressReceiver.setText(account.address)
                        binding.phoneReceiver.setText(account.phone)
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                System.out.println("FAILED ")
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}