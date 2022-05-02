package com.example.basalasa.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.basalasa.R
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
import java.text.DecimalFormat
import java.text.NumberFormat

class Checkout : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var hashMap: HashMap<String, BooksInCart>
    lateinit var adapter: CheckoutAdapter
    private var account: Account = Account()
    private val formatter: NumberFormat = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent: Intent = intent
        hashMap = intent.getSerializableExtra("map") as (HashMap<String, BooksInCart>)
        getInfo(this)
//        Log.d("account2", account.toString())

        loadList()
    }

    private fun loadList() {
        val token = Cache.getToken(this)
        if (token === null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
//        val response = MyAPI.getAPI().getCart(token.toString())
        adapter = CheckoutAdapter(hashMap)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this@Checkout)
        var total = 0
        val arrBook: ArrayList<BooksInCart> = ArrayList()
        for (i in 0 until hashMap.size) {
            val keyByIndex = hashMap.keys.elementAt(i) // Get key by index.
            val valueOfElement = hashMap.getValue(keyByIndex)
            total += valueOfElement.price * valueOfElement.quantity
            arrBook.add(valueOfElement)
        }
        binding.shippingCost.text = "30,000"

        binding.itemsCost.text = formatter.format(total)
        binding.totalCost.text = formatter.format(total + 30000)
        binding.submit.setOnClickListener {
            val email = binding.emailReceiver.text.toString()
            val phone = binding.phoneReceiver.text.toString()
            val address = binding.addressReceiver.text.toString()

            if(email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this@Checkout, "Please fill all the field", Toast.LENGTH_SHORT).show()
            } else {
                val alertDialog: AlertDialog? = this.let {
                    val builder = AlertDialog.Builder(this@Checkout!!)
                    builder.apply {
                        setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                            val response = MyAPI.getAPI().postItemCheckout(
                                token.toString(),
                                CheckoutBody(arrBook, email, address, phone)
                            )
                            response.enqueue(object : Callback<CheckoutResponse> {
                                override fun onResponse(
                                    call: Call<CheckoutResponse>,
                                    response: Response<CheckoutResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        println("SUCCESS")
                                        val intent2 = Intent(this@Checkout, Cart::class.java)
                                        startActivity(intent2)
                                        Toast.makeText(this@Checkout, "Checkout successfully", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                }

                                override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                                    Toast.makeText(this@Checkout, "Fail connection to server", Toast.LENGTH_LONG)
                                        .show()
                                    t.printStackTrace()
                                }

                            })
                        })
                        setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                            //do sth
                        })
//                                setIcon(android.R.drawable.ic_dialog_alert)
                        setTitle("Process to checkout")
                    }
                    builder.create()
                }
                alertDialog!!.show()
            }
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
                        account = Account(data!!)
                        Log.d("account", account.toString())

                        binding.emailReceiver.setText(account.email)
                        binding.addressReceiver.setText(account.address)
                        binding.phoneReceiver.setText(account.phone)
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                println("FAILED ")
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}