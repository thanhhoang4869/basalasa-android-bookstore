package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basalasa.databinding.FragmentCategoryBinding
import com.example.basalasa.databinding.FragmentCustomerOrderArrivedBinding
import com.example.basalasa.databinding.FragmentCustomerOrderWaitingBinding
import com.example.basalasa.databinding.FragmentHomeBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.Category

class CustomerOrderArrived : Fragment() {
    private var _binding: FragmentCustomerOrderArrivedBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerOrderArrivedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}