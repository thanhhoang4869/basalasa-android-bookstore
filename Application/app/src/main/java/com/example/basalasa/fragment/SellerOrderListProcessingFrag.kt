package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.activity.SellerOrderDetails
import com.example.basalasa.adapter.SellerPendingOrderAdapter
import com.example.basalasa.adapter.SellerProcessingOrderAdapter
import com.example.basalasa.databinding.FragmentSellerOrderListPendingBinding
import com.example.basalasa.databinding.FragmentSellerOrderListProcessingBinding
import com.example.basalasa.model.body.CancelOrderBody
import com.example.basalasa.model.entity.SellerPendingOrder
import com.example.basalasa.model.reponse.CancelOrderResponse
import com.example.basalasa.model.reponse.GetSellerPendingOrderResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerOrderListProcessingFrag(private val user: String) : Fragment() {
    private lateinit var _binding: FragmentSellerOrderListProcessingBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSellerOrderListProcessingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProcessing(user)
    }

    private fun loadProcessing(user: String) {
        val token= Cache.getToken(requireContext())
        val response= MyAPI.getAPI().getSellerProcessingOrder(token.toString(),user)

        response.enqueue(object : Callback<GetSellerPendingOrderResponse> {
            override fun onResponse(
                call: Call<GetSellerPendingOrderResponse>,
                response: Response<GetSellerPendingOrderResponse>
            ) {
                if(response.isSuccessful){
                    val orders= response.body()!!.orders
                    val adapter= SellerProcessingOrderAdapter(orders)
                    binding.rv.adapter=adapter
                    binding.rv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    adapter.onItemClick = { customerHistory->
                        SellerOrderDetails(customerHistory).show(this@SellerOrderListProcessingFrag.childFragmentManager,"bs")
                    }
                    adapter.onDeliver = { customerHistory ->
                        val alertDialog: AlertDialog = this.let {
                            val builder = AlertDialog.Builder(context!!)
                            builder.apply {
                                setPositiveButton("OK") { _, _ ->
                                    deliverOrder(customerHistory)
                                }
                                setNegativeButton("Cancel") { _, _ ->
                                }
                                setTitle("Deliver this order?")
                            }
                            builder.create()
                        }
                        alertDialog.show()
                    }
                    adapter.onDone = { customerHistory ->
                        val alertDialog: AlertDialog = this.let {
                            val builder = AlertDialog.Builder(context!!)
                            builder.apply {
                                setPositiveButton("OK") { _, _ ->
                                    doneOrder(customerHistory)
                                }
                                setNegativeButton("Cancel") { _, _ ->
                                }
                                setTitle("Confirm this order?")
                            }
                            builder.create()
                        }
                        alertDialog.show()
                    }
                }
            }

            override fun onFailure(call: Call<GetSellerPendingOrderResponse>, t: Throwable) {
            }
        })
    }
    private fun deliverOrder(customerHistory: SellerPendingOrder) {
        val token = context?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().deliverOrder(it, CancelOrderBody(customerHistory._id)) }

        response?.enqueue(object : Callback<CancelOrderResponse> {
            override fun onResponse(
                call: Call<CancelOrderResponse>,
                response: Response<CancelOrderResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data?.exitcode == 0) {
                        loadProcessing(user)
                        Toast.makeText(context, "The order is delivering", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Cannot cancel the order", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<CancelOrderResponse>, t: Throwable) {
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        })
    }

    private fun doneOrder(customerHistory: SellerPendingOrder) {
        val token = context?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().doneOrder(it, CancelOrderBody(customerHistory._id)) }

        response?.enqueue(object : Callback<CancelOrderResponse> {
            override fun onResponse(
                call: Call<CancelOrderResponse>,
                response: Response<CancelOrderResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data?.exitcode == 0) {
                        loadProcessing(user)
                        Toast.makeText(context, "The order was done", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Cannot confirm the order", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<CancelOrderResponse>, t: Throwable) {
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        })
    }
}