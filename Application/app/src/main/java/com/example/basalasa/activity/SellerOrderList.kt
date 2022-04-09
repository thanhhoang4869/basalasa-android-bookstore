package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basalasa.databinding.ActivitySellerOrderListBinding

class SellerOrderList : AppCompatActivity() {
    private lateinit var binding: ActivitySellerOrderListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellerOrderListBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
    }
}