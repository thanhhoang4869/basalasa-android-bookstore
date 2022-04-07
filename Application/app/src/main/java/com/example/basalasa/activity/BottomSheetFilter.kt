package com.example.basalasa.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.databinding.FragmentBottomSheetFilterBinding
import com.example.basalasa.model.body.FilterResultsBody
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.Category
import com.example.basalasa.model.reponse.GetCategoryResponse
import com.example.basalasa.model.reponse.GetFilterResultsResponse
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetFilter : BottomSheetDialogFragment() {
    private lateinit var _binding: FragmentBottomSheetFilterBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFilterBinding.inflate(inflater, container, false)

        loadCategoryList()

        binding.btnApply.setOnClickListener {
            val text=binding.grCategoryList.selectedButtons
            val selectedList= arrayListOf<String>()
            val min=binding.etMinPrice.text
            val max=binding.etMaxPrice.text

            for(item in text){
                selectedList.add(item.text)
            }

            loadFilterResults(FilterResultsBody(selectedList, min.toString(), max.toString()))
        }

        return binding.root
    }

    private fun loadCategoryList() {
        val response = MyAPI.getAPI().getCategory()
        val arrCategory = ArrayList<Category>()

        response.enqueue(object : Callback<GetCategoryResponse> {
            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Category in data!!.arrCategory) {
                        arrCategory.add(item)
                    }

                    val btnGroup=binding.grCategoryList
                    for(i in arrCategory){
                        val btn= ThemedButton(context!!)
                        btn.text=i.name
                        btn.bgColor="#FFFFFF".toColorInt()
                        btn.textColor="#000000".toColorInt()
                        btn.borderColor="#000000".toColorInt()
                        btn.borderWidth=5f
                        btn.selectedBgColor="#0ACF83".toColorInt()
                        btn.selectedTextColor="#FFFFFF".toColorInt()
                        btnGroup.addView(btn, ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ))
                    }
                }
            }
            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        })
    }

    private fun loadFilterResults(body:FilterResultsBody){
        val response = MyAPI.getAPI().getFilterResults(body)
        val arrBooks = ArrayList<Book>()

        response.enqueue(object : Callback<GetFilterResultsResponse> {
            override fun onResponse(call: Call<GetFilterResultsResponse>, response: Response<GetFilterResultsResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data!!.filterResults) {
                        arrBooks.add(item)
                    }

                    val adapter= CategoryAdapter(arrBooks)
                    val searchList=activity!!.findViewById<RecyclerView>(R.id.searchList)
                    searchList.adapter=adapter
                    searchList.layoutManager = GridLayoutManager(activity,2)
                    adapter.setOnItemClickListener(object : CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(activity,BookDetail::class.java)
                            intent.putExtra("id",arrBooks[position]._id)
                            activity!!.startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetFilterResultsResponse>, t: Throwable) {
                Toast.makeText(activity, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}