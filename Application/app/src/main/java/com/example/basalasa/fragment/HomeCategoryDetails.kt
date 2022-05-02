package com.example.basalasa.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.activity.BookDetail
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.adapter.HomeSaleAdapter
import com.example.basalasa.databinding.FragmentHomeBinding
import com.example.basalasa.databinding.FragmentHomeCategoryDetailsBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.reponse.CategoryDetailsResponse
import com.example.basalasa.model.reponse.GetBooksResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCategoryDetails(val category:String) : Fragment() {
    private var _binding: FragmentHomeCategoryDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCategoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response=MyAPI.getAPI().getCategoryDetails(category)
        response.enqueue(object : Callback<GetBooksResponse> {
            override fun onResponse(
                call: Call<GetBooksResponse>,
                response: Response<GetBooksResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.arrBook
                    val adapter=CategoryAdapter(data!!)
                    binding.rv.adapter=adapter
                    binding.rv.layoutManager=GridLayoutManager(context,2)
                    adapter.setOnItemClickListener(object :CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent=Intent(activity,BookDetail::class.java)
                            intent.putExtra("id",data[position]._id)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetBooksResponse>, t: Throwable) {
            }
        })
    }
}