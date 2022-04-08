package com.example.basalasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basalasa.fragment.CustomerOrderArrived
import com.example.basalasa.fragment.CustomerOrderCanceled
import com.example.basalasa.fragment.CustomerOrderProcess
import com.example.basalasa.fragment.CustomerOrderWaiting

class CustomerOrderAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4;
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return CustomerOrderWaiting()
            1 -> return CustomerOrderProcess()
            2 -> return CustomerOrderArrived()
            3 -> return CustomerOrderCanceled()
            else -> {
                return Fragment();
            }
        }
    }
}