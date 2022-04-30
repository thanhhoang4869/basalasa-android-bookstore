package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basalasa.adapter.SellerBookManagementAdapter
import com.example.basalasa.adapter.SellerOrderListAdapter
import com.example.basalasa.databinding.ActivitySellerBookManagementBinding
import com.example.basalasa.databinding.ActivitySellerOrderListBinding
import com.google.android.material.tabs.TabLayoutMediator

class SellerBookManagement : AppCompatActivity() {
    private lateinit var binding: ActivitySellerBookManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellerBookManagementBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val user=intent.getStringExtra("user")
        binding.vpSellerOrderList.adapter=SellerBookManagementAdapter(this,user!!)

        TabLayoutMediator(binding.tlSellerOrderList,binding.vpSellerOrderList) { tab, position ->
            when (position) {
                0 -> tab.text = "Your books"
                1 -> tab.text = "Add book"
            }
        }.attach()
    }
}