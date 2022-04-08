package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basalasa.R
import com.example.basalasa.adapter.CustomerOrderAdapter
import com.example.basalasa.databinding.ActivityCustomerOrderBinding
import com.example.basalasa.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class CustomerOrder : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.customerOrderViewPager2.adapter = CustomerOrderAdapter(this)

        TabLayoutMediator(binding.customerOrderTabLayout, binding.customerOrderViewPager2,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    when (position) {
                        0 -> tab.text = "Waiting"
                        1 -> tab.text = "Process"
                        2 -> tab.text = "Arrived"
                        3 -> tab.text = "Canceled"
                    }
                }).attach()


    }
}