package com.example.basalasa.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.basalasa.R
import com.example.basalasa.activity.*
import com.example.basalasa.databinding.FragmentSettingsBinding
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.model.reponse.SendRequestResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    lateinit var account: Account
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("test","attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getInfo()
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.reqBtn.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.roleTxt.isVisible = false

        binding.reqBtn.setOnClickListener { sendRequest() }

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

        binding.productBtn.setOnClickListener {
            val intent = Intent(context, SellerProductList::class.java)
            intent.putExtra("user", account.email)
            startActivity(intent)
        }

        binding.orderListBtn.setOnClickListener {
            val intent = Intent(context, SellerOrderList::class.java)
            intent.putExtra("user", account.email)
            startActivity(intent)
        }

        binding.orderBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(context, CustomerOrder::class.java)
                it.startActivity(intent)
            }
        }

        binding.accountListBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(context, AccountList::class.java)
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
        super.onViewCreated(view, savedInstanceState)
        binding.reqBtn.setOnClickListener { sendRequest() }
    }

    private fun loadInfo(account: Account) {
        binding.email.text=account.email
        binding.name.text = account.fullName
    }

    private fun sendRequest() {
        val token = context?.let { Cache.getToken(it) }!!
        val response = MyAPI.getAPI().postSendRequest(token)

        response.enqueue(object : Callback<SendRequestResponse> {
            override fun onResponse(
                call: Call<SendRequestResponse>,
                response: Response<SendRequestResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    when (data?.exitcode) {
                        0 -> {
                            reload()
                        }
                        403 -> {
                            Toast.makeText(
                                context,
                                "Token expired",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        500 -> {
                            Toast.makeText(
                                context,
                                "Fail to send request",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SendRequestResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG)
                    .show()
                t.printStackTrace();
            }
        })
    }

    private fun getInfo() {
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
                        account = Account(data)
                        loadInfo(account)

                        when (account.role) {
                            0 -> {
                                setViewUser()
                            }
                            1 -> {
                                setViewSeller()
                            }
                            else -> {
                                setViewAdmin()
                            }
                        }
                    } else {
                        Log.d("alo", "1234")
                    }
                }
            }

            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
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

    fun reload() {
        parentFragmentManager.beginTransaction().detach(this).commit();
        parentFragmentManager.beginTransaction().attach(this).commit();
    }

    fun setViewUser() {
        binding.accountListBtn.isVisible = false
        binding.accountListBtnDivider.isVisible = false
        binding.orderListBtn.isVisible = false
        binding.orderListBtnDivider.isVisible = false
        binding.productBtn.isVisible = false
        binding.productBtnDivider.isVisible = false
        binding.requestBtn.isVisible = false
        binding.requestBtnDivider.isVisible = false
        if (account.request == 1) {
            binding.reqBtn.isVisible = false
            binding.roleTxt.isVisible = true
            binding.roleTxt.text = "Your request is in process..."
            binding.roleTxt.setTextColor(Color.parseColor("#808080"))
        }
    }

    fun setViewSeller() {
        binding.accountListBtn.isVisible = false
        binding.accountListBtnDivider.isVisible = false
        binding.requestBtn.isVisible = false
        binding.requestBtnDivider.isVisible = false
        binding.roleTxt.isVisible = true
        binding.reqBtn.isVisible = false
    }

    fun setViewAdmin() {
        binding.orderBtn.isVisible = false
        binding.orderBtnDivider.isVisible = false
        binding.orderListBtn.isVisible = false
        binding.orderListBtnDivider.isVisible = false
        binding.productBtn.isVisible = false
        binding.productBtnDivider.isVisible = false
        binding.reqLayout.isVisible = false
    }
}