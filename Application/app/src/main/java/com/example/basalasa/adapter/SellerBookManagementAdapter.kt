package com.example.basalasa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basalasa.fragment.*

class SellerBookManagementAdapter(fragment: FragmentActivity, private val user: String): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SellerViewBookFrag(user)
            1 -> SellerAddBookFrag(user)
            2 -> SellerUpdateBookFrag(user)
            3 -> SellerDeleteBookFrag(user)
            else -> {
                Fragment()
            }
        }
    }
}