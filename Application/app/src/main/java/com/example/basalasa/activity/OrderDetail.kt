package com.example.basalasa.activity


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.adapter.OrderDetailAdapter
import com.example.basalasa.databinding.FragmentOrderDetailBinding
import com.example.basalasa.model.entity.CustomerHistory


import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OrderDetail(private var arrHistory: CustomerHistory): BottomSheetDialogFragment(){
    private lateinit var _binding: FragmentOrderDetailBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        _binding.orderDetailItem.adapter=OrderDetailAdapter(arrHistory.product)


        _binding.orderDetailItem.layoutManager=LinearLayoutManager(container?.context)
        _binding.customerOrderTotalMoney.text=arrHistory.total.toString()

        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}