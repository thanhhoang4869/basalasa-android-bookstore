package com.example.basalasa.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColorInt
import com.example.basalasa.databinding.FragmentBottomSheetFilterBinding
import com.example.basalasa.model.body.FilterResultsBody
import com.example.basalasa.model.entity.Category
import com.example.basalasa.model.reponse.GetCategoryResponse
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSheetFilter : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentBottomSheetFilterBinding
    private val binding get() = _binding
    private var mListener: BottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFilterBinding.inflate(inflater, container, false)

        loadCategoryList()

        binding.btnApply.setOnClickListener {
            val text = binding.grCategoryList.selectedButtons
            val selectedList = arrayListOf<String>()
            val min = binding.etMinPrice.text
            val max = binding.etMaxPrice.text

            for (item in text) {
                selectedList.add(item.text)
            }

            mListener!!.onButtonClicked(
                FilterResultsBody(
                    selectedList,
                    min.toString(),
                    max.toString()
                )
            )
            dismiss()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = try {
            context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement BottomSheetListener"
            )
        }
    }

    interface BottomSheetListener {
        fun onButtonClicked(body: FilterResultsBody)
    }

    private fun loadCategoryList() {
        val response = MyAPI.getAPI().getCategory()
        val arrCategory = ArrayList<Category>()

        response.enqueue(object : Callback<GetCategoryResponse> {
            override fun onResponse(
                call: Call<GetCategoryResponse>,
                response: Response<GetCategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for (item: Category in data!!.arrCategory) {
                        arrCategory.add(item)
                    }

                    val btnGroup = binding.grCategoryList
                    for (i in arrCategory) {
                        val btn = ThemedButton(context!!)
                        btn.text = i.name
                        btn.bgColor = "#FFFFFF".toColorInt()
                        btn.textColor = "#000000".toColorInt()
                        btn.borderColor = "#000000".toColorInt()
                        btn.borderWidth = 5f
                        btn.selectedBgColor = "#0ACF83".toColorInt()
                        btn.selectedTextColor = "#FFFFFF".toColorInt()
                        btnGroup.addView(
                            btn, ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        })
    }
}