package com.example.basalasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.databinding.ActivitySearchResultsBinding
import com.example.basalasa.model.body.FilterResultsBody
import com.example.basalasa.model.body.SearchResultsBody
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.reponse.GetFilterResultsResponse
import com.example.basalasa.model.reponse.GetSearchResultsResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResults : AppCompatActivity(),BottomSheetFilter.BottomSheetListener {
    private lateinit var binding: ActivitySearchResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnFilter.setOnClickListener {
            BottomSheetFilter().show(this.supportFragmentManager,"bs")
        }

        loadSearchResults(intent.getStringExtra("searchInput")!!)
    }

    private fun loadSearchResults(input:String){
        val response = MyAPI.getAPI().getSearchResults(SearchResultsBody(input))
        val arrBooks = ArrayList<Book>()

        response.enqueue(object : Callback<GetSearchResultsResponse> {
            override fun onResponse(call: Call<GetSearchResultsResponse>, response: Response<GetSearchResultsResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data!!.searchResults) {
                        arrBooks.add(item)
                    }

                    //bind to adapter
                    val adapter= CategoryAdapter(arrBooks)
                    binding.searchList.adapter = adapter
                    binding.searchList.layoutManager = GridLayoutManager(this@SearchResults,2)
                    adapter.setOnItemClickListener(object :CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@SearchResults,BookDetail::class.java)
                            intent.putExtra("id",arrBooks[position]._id)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetSearchResultsResponse>, t: Throwable) {
                Toast.makeText(this@SearchResults, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

    override fun onButtonClicked(body:FilterResultsBody) {
        loadFilterResults(body)
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
                    binding.searchList.adapter = adapter
                    binding.searchList.layoutManager = GridLayoutManager(this@SearchResults,2)
                    adapter.setOnItemClickListener(object :CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@SearchResults,BookDetail::class.java)
                            intent.putExtra("id",arrBooks[position]._id)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetFilterResultsResponse>, t: Throwable) {
                Toast.makeText(this@SearchResults, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}