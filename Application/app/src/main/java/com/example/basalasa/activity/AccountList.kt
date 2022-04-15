package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.adapter.AccountListAdapter
import com.example.basalasa.databinding.ActivityAccountListBinding
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.reponse.GetAccountListResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountList : AppCompatActivity() {
    private lateinit var binding: ActivityAccountListBinding
    private lateinit var accList:ArrayList<Account>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getAccList()
    }

    private fun getAccList(){
        val token = Cache.getToken(this)
        val response = MyAPI.getAPI().getAccountList(token.toString())

        response.enqueue(object : Callback<GetAccountListResponse> {
            override fun onResponse(
                call: Call<GetAccountListResponse>,
                response: Response<GetAccountListResponse>
            ) {
                if (response.isSuccessful) {

                    val data = response.body()
                    if (data?.exitcode == 0) {
                        accList= data.accList
                        Log.d("accLs",accList.toString())
                        binding.lsAcc.adapter=AccountListAdapter(accList)
                        binding.lsAcc.layoutManager = LinearLayoutManager(this@AccountList)

                        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(
                            this@AccountList,
                            DividerItemDecoration.VERTICAL
                        )
                        binding.lsAcc.addItemDecoration(itemDecoration)
                    } else{
                        Log.d("test",data?.exitcode.toString())
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountListResponse>, t: Throwable) {
                Log.d("Alo","fail")
                Toast.makeText(this@AccountList, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}