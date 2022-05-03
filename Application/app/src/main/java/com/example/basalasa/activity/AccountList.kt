package com.example.basalasa.activity

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.adapter.AccountListAdapter
import com.example.basalasa.databinding.ActivityAccountListBinding
import com.example.basalasa.model.body.ChangeAccStateBody
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.reponse.ChangeAccStateResponse
import com.example.basalasa.model.reponse.GetAccountListResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountList : AppCompatActivity() {
    private lateinit var binding: ActivityAccountListBinding
    private lateinit var accList: ArrayList<Account>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.noAccTxt.isVisible=false

        getAccList()
    }

    private fun getAccList() {
        val token = Cache.getToken(this)
        val response = MyAPI.getAPI().getAccountList(token.toString())

        response.enqueue(object : Callback<GetAccountListResponse> {
            override fun onResponse(
                call: Call<GetAccountListResponse>,
                response: Response<GetAccountListResponse>
            ) {
                if (response.isSuccessful) {

                    val data = response.body()

                    when (data?.exitcode) {
                        0 -> {
                            setUpView(data)
                        }
                        403 -> {
                            Toast.makeText(this@AccountList, "Token expired", Toast.LENGTH_LONG)
                                .show()
                        }
                        104 -> {
                            binding.noAccTxt.isVisible=true
                            binding.lsAcc.isVisible=false
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountListResponse>, t: Throwable) {
                Toast.makeText(this@AccountList, "Fail connection to server", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace()
            }
        })
    }

    fun setUpView(dataTmp: GetAccountListResponse) {
        accList = dataTmp.accList
        val adapter = AccountListAdapter(accList)
        binding.lsAcc.adapter = adapter
        binding.lsAcc.layoutManager = LinearLayoutManager(this@AccountList)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(
            this@AccountList,
            DividerItemDecoration.VERTICAL
        )
        binding.lsAcc.addItemDecoration(itemDecoration)

        adapter.onButtonClick = { pos ->
            val email = accList[pos].email
            val newState: Int = 1 - accList[pos].status

            val token = Cache.getToken(this)!!

            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(this@AccountList)
                builder.apply {
                    setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, id ->
                        val response = MyAPI.getAPI().postChangeAccState(token, ChangeAccStateBody(email, newState))

                        response.enqueue(object : Callback<ChangeAccStateResponse> {
                            override fun onResponse(
                                call: Call<ChangeAccStateResponse>,
                                response: Response<ChangeAccStateResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val data = response.body()
                                    when (data?.exitcode) {
                                        0 -> {
                                            accList[pos].status = newState
                                            adapter.notifyItemChanged(pos)
                                        }
                                        403 -> {
                                            Toast.makeText(
                                                this@AccountList,
                                                "Token expired",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ChangeAccStateResponse>, t: Throwable) {
                                Toast.makeText(this@AccountList, "Fail connection to server", Toast.LENGTH_LONG)
                                    .show()
                                t.printStackTrace();
                            }
                        })
                    })
                    setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        //do sth
                    })
                    if(newState == 0) {
                        setTitle("Do you really want to ban $email ?")
                    } else {
                        setTitle("Do you really want to unban $email")
                    }
                }
                builder.create()
            }
            alertDialog!!.show()
        }
    }
}