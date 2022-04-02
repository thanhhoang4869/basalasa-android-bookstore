package com.example.basalasa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.adapter.HomeCategoryAdapter
import com.example.basalasa.adapter.HomePageViewerAdapter
import com.example.basalasa.adapter.HomeSaleAdapter
import com.example.basalasa.databinding.FragmentHomeBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.Category
import com.example.basalasa.model.reponse.GetBookOnSaleResponse
import com.example.basalasa.model.reponse.GetCategoryResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null

    lateinit var arrCategory: ArrayList<Category>
    lateinit var arrBookOnSale: ArrayList<Book>

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeViewPager.adapter = context?.let { HomePageViewerAdapter(it) }

        loadBookOnSaleList()
        loadCategoryList()
    }

    private fun loadCategoryList() {
        val response = MyAPI.getAPI().getCategory()
        arrCategory = ArrayList()

        response.enqueue(object : Callback<GetCategoryResponse> {
            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Category in data!!.arrCategory!!) {
                        arrCategory.add(item)
                    }

                    //bind to adapter
                    binding.homeCategoryRC.adapter = HomeCategoryAdapter(arrCategory)
                    binding.homeCategoryRC.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                if(isAdded){
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace();
                }
            }
        })
    }

    private fun loadBookOnSaleList() {
        val response = MyAPI.getAPI().getBookOnSale()
        arrBookOnSale = ArrayList()

        response.enqueue(object : Callback<GetBookOnSaleResponse> {
            override fun onResponse(call: Call<GetBookOnSaleResponse>, response: Response<GetBookOnSaleResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data?.arrBookOnSale!!) {
                        arrBookOnSale.add(item)
                    }

                    //bind to adapter
                    binding.homeSaleRC.adapter = HomeSaleAdapter(arrBookOnSale)
                    binding.homeSaleRC.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
            override fun onFailure(call: Call<GetBookOnSaleResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace();
            }
        })
    }

}