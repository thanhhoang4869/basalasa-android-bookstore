package com.example.basalasa.activity

import android.R.id.tabs
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.basalasa.adapter.SellerBookManagementAdapter
import com.example.basalasa.databinding.ActivitySellerBookManagementBinding
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
                0 -> tab.text = "My books"
                1 -> tab.text = "Add book"
            }
        }.attach()
    }
}