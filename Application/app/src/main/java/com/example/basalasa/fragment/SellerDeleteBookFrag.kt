package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.activity.SellerOrderDetails
import com.example.basalasa.adapter.SellerCompletedOrderAdapter
import com.example.basalasa.adapter.SellerProcessingOrderAdapter
import com.example.basalasa.databinding.FragmentCustomerOrderCompletedBinding
import com.example.basalasa.databinding.FragmentSellerOrderListCompleteBinding
import com.example.basalasa.databinding.FragmentSellerOrderListProcessingBinding
import com.example.basalasa.model.reponse.GetSellerPendingOrderResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerDeleteBookFrag(private val user: String) : Fragment() {
    private lateinit var _binding: FragmentSellerOrderListCompleteBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSellerOrderListCompleteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCompleted(user)
    }

    private fun loadCompleted(user: String) {
        val token= Cache.getToken(requireContext())
        val response= MyAPI.getAPI().getSellerCompletedOrder(token.toString(),user)

        response.enqueue(object : Callback<GetSellerPendingOrderResponse> {
            override fun onResponse(
                call: Call<GetSellerPendingOrderResponse>,
                response: Response<GetSellerPendingOrderResponse>
            ) {
                if(response.isSuccessful){
                    val orders= response.body()!!.orders
                    val adapter= SellerCompletedOrderAdapter(orders)
                    binding.rv.adapter=adapter
                    binding.rv.layoutManager=
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    adapter.onItemClick = { customerHistory->
                        SellerOrderDetails(customerHistory).show(this@SellerDeleteBookFrag.childFragmentManager,"bs")
                    }
                }
            }

            override fun onFailure(call: Call<GetSellerPendingOrderResponse>, t: Throwable) {
            }
        })
    }
}