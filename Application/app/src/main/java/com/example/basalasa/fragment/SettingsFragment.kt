package com.example.basalasa.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.basalasa.R
import com.example.basalasa.activity.MainActivity
import com.example.basalasa.databinding.FragmentSettingsBinding
import com.example.basalasa.activity.SettingChangeInformation
import com.example.basalasa.activity.SettingChangePassword
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.model.entity.Account
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    lateinit var account: Account

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        getInfo()
        binding.changeInfo.setOnClickListener {
            activity?.let {
                val intent = Intent(context, SettingChangeInformation::class.java)
                it.startActivity(intent)
            }
        }

        binding.changePass.setOnClickListener {
            activity?.let {
                val intent = Intent(context, SettingChangePassword::class.java)
                intent.putExtra("email", account.email)
                it.startActivity(intent)
            }
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("alo","1234 create")
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadInfo(account: Account) {
        binding.email.text = account.email
        binding.name.text = account.fullName
    }

    private fun getInfo(){
        val token = context?.let { Cache.getToken(it) }
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
                    } else{
                        Log.d("alo","1234")
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                Log.d("Alo","fail")
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace();
                }
            }
        })
    }

    private fun logout() {
        activity?.let {
            Cache.clear(it)
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }
}