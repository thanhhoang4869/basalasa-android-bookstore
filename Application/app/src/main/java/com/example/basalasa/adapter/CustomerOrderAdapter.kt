package com.example.basalasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basalasa.fragment.*

class CustomerOrderAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 5;
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return CustomerOrderPending()
            1 -> return CustomerOrderPreparing()
            2 -> return CustomerOrderArriving()
            3 -> return CustomerOrderCompleted()
            4 -> return CustomerOrderCanceled()
            else -> {
                return Fragment();
            }
        }
    }
}