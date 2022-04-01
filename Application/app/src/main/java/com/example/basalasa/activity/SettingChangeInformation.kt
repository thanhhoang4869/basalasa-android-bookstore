package com.example.basalasa.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.basalasa.databinding.ActivitySettingChangeInformationBinding
import com.example.basalasa.model.body.ChangeInformationBody
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.reponse.ChangeInformationResponse
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingChangeInformation : AppCompatActivity() {
    private lateinit var binding: ActivitySettingChangeInformationBinding

    lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingChangeInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getInfo(this)

        binding.changeInfoSaveBtn.setOnClickListener {
            saveInfo(this)
        }
    }

    private fun loadInfo(account: Account) {
        binding.changeInfoFullNameTextEdit.setText(account.fullName, TextView.BufferType.EDITABLE)
        binding.changeInfoPhoneTextEdit.setText(account.phone, TextView.BufferType.EDITABLE)
        binding.changeInfoAddressTextEdit.setText(account.address, TextView.BufferType.EDITABLE)
    }

    private fun getInfo(context: Context) {
        val token = context.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().getAccount(it) }

        response?.enqueue(object : Callback<GetAccountResponse> {
            override fun onResponse(
                call: Call<GetAccountResponse>,
                response: Response<GetAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        account= Account(data)
                        loadInfo(account)
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

    private fun saveInfo(context: Context) {
        val token = context.let { Cache.getToken(it) }

        val fullName = binding.changeInfoFullNameTextEdit.text.toString()
        val phone = binding.changeInfoPhoneTextEdit.text.toString()
        val address = binding.changeInfoAddressTextEdit.text.toString()

        val response = token?.let {MyAPI.getAPI().changeInfo(it, ChangeInformationBody(account.email, fullName, phone, address))}

        response?.enqueue(object : Callback<ChangeInformationResponse> {
            override fun onResponse(call: Call<ChangeInformationResponse>, response: Response<ChangeInformationResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<ChangeInformationResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace();
            }
        })
    }
}