package com.example.basalasa.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.adapter.SellerOrderDetailsAdapter
import com.example.basalasa.databinding.FragmentOrderDetailBinding
import com.example.basalasa.model.entity.SellerPendingOrder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat
import java.text.NumberFormat

class SellerOrderDetails(private var arrHistory: SellerPendingOrder): BottomSheetDialogFragment(){
    private lateinit var _binding: FragmentOrderDetailBinding
    private val binding get() = _binding
    private val formatter: NumberFormat = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        _binding.orderDetailItem.adapter=SellerOrderDetailsAdapter(arrHistory.product)
        _binding.orderDetailItem.layoutManager=LinearLayoutManager(container?.context)

        val tmp = arrHistory.total

        _binding.customerOrderTotalMoney.text=formatter.format(tmp)
        return binding.root
    }
}