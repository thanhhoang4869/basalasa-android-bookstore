package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basalasa.adapter.SellerOrderListAdapter
import com.example.basalasa.databinding.ActivitySellerOrderListBinding
import com.google.android.material.tabs.TabLayoutMediator

class SellerOrderList : AppCompatActivity() {
    private lateinit var binding: ActivitySellerOrderListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellerOrderListBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val user=intent.getStringExtra("user")
        binding.vpSellerOrderList.adapter=SellerOrderListAdapter(this,user!!)

        TabLayoutMediator(binding.tlSellerOrderList,binding.vpSellerOrderList) { tab, position ->
            when (position) {
                0 -> tab.text = "Pending"
                1 -> tab.text = "Processing"
                2 -> tab.text = "Completed"
                3 -> tab.text="Canceled"
            }
        }.attach()
    }
}