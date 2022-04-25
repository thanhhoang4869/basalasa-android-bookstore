package com.example.basalasa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.adapter.CustomerOrderTabRCAdapter
import com.example.basalasa.adapter.SellerPendingOrderAdapter
import com.example.basalasa.databinding.FragmentSellerOrderListPendingBinding
import com.example.basalasa.model.reponse.GetCustomerHistoryResponse
import com.example.basalasa.model.reponse.GetSellerPendingOrderResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderListPendingFrag(private val user: String) : Fragment() {
    private lateinit var _binding:FragmentSellerOrderListPendingBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSellerOrderListPendingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPending(user)
    }

    private fun loadPending(user: String) {
        val token=Cache.getToken(requireContext())
        val response=MyAPI.getAPI().getSellerPendingOrder(token.toString(),user)

        response.enqueue(object :Callback<GetSellerPendingOrderResponse>{
            override fun onResponse(
                call: Call<GetSellerPendingOrderResponse>,
                response: Response<GetSellerPendingOrderResponse>
            ) {
                if(response.isSuccessful){
                    val orders= response.body()!!.orders
                    val adapter=SellerPendingOrderAdapter(orders)
                    Log.i("?","orders[0].product[0].picture")
                    binding.rv.adapter=adapter
                    binding.rv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                }
            }

            override fun onFailure(call: Call<GetSellerPendingOrderResponse>, t: Throwable) {
            }
        })
    }
}