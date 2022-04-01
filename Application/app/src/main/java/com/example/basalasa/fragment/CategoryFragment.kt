package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basalasa.R
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.databinding.FragmentCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCategoryListItem.layoutManager = GridLayoutManager(activity, 2)
        binding.rvCategoryListItem.adapter = CategoryAdapter()

        binding.filterBtn.setOnClickListener {
            BottomSheetFilter().show(requireActivity().supportFragmentManager, "bs")
        }
    }
}

class BottomSheetFilter : BottomSheetDialogFragment() {
    private var btnCable: ToggleButton? = null
    private var btnNovel: ToggleButton? = null
    private var btnEdu: ToggleButton? = null
    private var btnForeign: ToggleButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =
            inflater.inflate(R.layout.fragment_category_bottom_sheet_filter, container, false)

        btnCable = rootView.findViewById(R.id.togbtnCable)
        btnNovel = rootView.findViewById(R.id.togbtnNovel)
        btnEdu = rootView.findViewById(R.id.togbtnEdu)
        btnForeign = rootView.findViewById(R.id.togbtnForeign)

        return rootView
    }
}