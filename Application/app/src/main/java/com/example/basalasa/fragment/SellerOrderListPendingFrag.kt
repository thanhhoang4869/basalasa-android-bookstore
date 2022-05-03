package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.activity.SellerOrderDetails
import com.example.basalasa.adapter.SellerPendingOrderAdapter
import com.example.basalasa.databinding.FragmentSellerOrderListPendingBinding
import com.example.basalasa.model.body.CancelOrderBody
import com.example.basalasa.model.entity.SellerPendingOrder
import com.example.basalasa.model.reponse.CancelOrderResponse
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

    override fun onResume() {
        super.onResume()
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
                    binding.rv.adapter=adapter
                    binding.rv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    adapter.onCancel = { customerHistory ->
                        val alertDialog: AlertDialog = this.let {
                            val builder = AlertDialog.Builder(context!!)
                            builder.apply {
                                setPositiveButton("OK") { _, _ ->
                                    cancelOrder(customerHistory)
                                }
                                setNegativeButton("Cancel") { _, _ ->
                                }
                                setTitle("Are you sure to cancel this order?")
                            }
                            builder.create()
                        }
                        alertDialog.show()
                    }
                    adapter.onConfirm = { customerHistory ->
                        val alertDialog: AlertDialog = this.let {
                            val builder = AlertDialog.Builder(context!!)
                            builder.apply {
                                setPositiveButton("Confirm") { _, _ ->
                                    confirmOrder(customerHistory)
                                }
                                setNegativeButton("Cancel") { _, _ ->
                                }
                                setTitle("Are you sure to confirm this order?")
                            }
                            builder.create()
                        }
                        alertDialog.show()
                    }
                    adapter.onItemClick = { customerHistory->
                        SellerOrderDetails(customerHistory).show(this@SellerOrderListPendingFrag.childFragmentManager,"bs")
                    }
                }
            }

            override fun onFailure(call: Call<GetSellerPendingOrderResponse>, t: Throwable) {
            }
        })
    }

    private fun cancelOrder(customerHistory: SellerPendingOrder) {
        val token = context?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().cancelOrder(it, CancelOrderBody(customerHistory._id)) }

        response?.enqueue(object : Callback<CancelOrderResponse> {
            override fun onResponse(
                call: Call<CancelOrderResponse>,
                response: Response<CancelOrderResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data?.exitcode == 0) {
                        loadPending(user)
                        Toast.makeText(context, "The order is cancelled", Toast.LENGTH_LONG).show()
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

    private fun confirmOrder(customerHistory: SellerPendingOrder) {
        val token = context?.let { Cache.getToken(it) }
        val response = token?.let { MyAPI.getAPI().confirmOrder(it, CancelOrderBody(customerHistory._id)) }

        response?.enqueue(object : Callback<CancelOrderResponse> {
            override fun onResponse(
                call: Call<CancelOrderResponse>,
                response: Response<CancelOrderResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data?.exitcode == 0) {
                        loadPending(user)
                        Toast.makeText(context, "The order was successfully confirmed", Toast.LENGTH_LONG).show()
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