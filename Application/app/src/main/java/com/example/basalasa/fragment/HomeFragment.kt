package com.example.basalasa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.basalasa.R
import com.example.basalasa.model.GetCategoryResponse
import com.example.basalasa.model.entity.Category
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        var arrCategory = loadCategoryList()
        Log.i("arrCategory", arrCategory.toString())

        return rootView
    }

    private fun loadCategoryList(): ArrayList<Category>? {
        var arrCategory: ArrayList<Category>? = ArrayList()
        val response = MyAPI.getAPI().getCategory()

        response.enqueue(object : Callback<GetCategoryResponse> {
            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    arrCategory = data?.arrCategory
                }
            }

            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace();
            }
        })

        return arrCategory
    }

}