package com.example.basalasa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.adapter.CustomerOrderTabRCAdapter
import com.example.basalasa.databinding.FragmentCustomerOrderPendingBinding
import com.example.basalasa.databinding.FragmentCustomerOrderPreparingBinding
import com.example.basalasa.model.body.GetHistoryBody
import com.example.basalasa.model.entity.Account
import com.example.basalasa.model.entity.CustomerBookHistory
import com.example.basalasa.model.entity.CustomerHistory
import com.example.basalasa.model.reponse.GetAccountResponse
import com.example.basalasa.model.reponse.GetCustomerHistoryResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerOrderPreparing : Fragment() {
    private var _binding: FragmentCustomerOrderPreparingBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerOrderPreparingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadHistory()
    }

    private fun loadHistory(){
        val token = context?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().getHistory(it, GetHistoryBody("Preparing")) }

        response?.enqueue(object : Callback<GetCustomerHistoryResponse> {
            override fun onResponse(
                call: Call<GetCustomerHistoryResponse>,
                response: Response<GetCustomerHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val arrHistory: ArrayList<CustomerHistory>? = data?.arrHistory
                    if(!arrHistory!!.isEmpty()) {
                        binding.customerOrderPreparingRC.adapter = CustomerOrderTabRCAdapter(arrHistory!!, false)
                        binding.customerOrderPreparingRC.layoutManager = LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false)
                        binding.customerOrderPreparingNoInfo.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<GetCustomerHistoryResponse>, t: Throwable) {
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        })
    }
}