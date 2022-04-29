package com.example.basalasa.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.activity.BookDetail
import com.example.basalasa.activity.SellerOrderDetails
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.adapter.SellerCompletedOrderAdapter
import com.example.basalasa.adapter.SellerProcessingOrderAdapter
import com.example.basalasa.databinding.FragmentCustomerOrderCompletedBinding
import com.example.basalasa.databinding.FragmentSellerOrderListCompleteBinding
import com.example.basalasa.databinding.FragmentSellerOrderListProcessingBinding
import com.example.basalasa.databinding.FragmentSellerViewBookBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.reponse.GetBooksResponse
import com.example.basalasa.model.reponse.GetSellerPendingOrderResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerViewBookFrag(private val user: String) : Fragment() {
    private lateinit var _binding: FragmentSellerViewBookBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSellerViewBookBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadListBook(user)
    }

    private fun loadListBook(user:String){
        val response = MyAPI.getAPI().getBooks()
        val arrBooks = arrayListOf<Book>()

        response.enqueue(object : Callback<GetBooksResponse> {
            override fun onResponse(call: Call<GetBooksResponse>, response: Response<GetBooksResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data!!.arrBook!!) {
                        if(item.seller==user)
                            arrBooks.add(item)
                    }

                    val adapter= CategoryAdapter(arrBooks)
                    binding.rv.adapter = adapter
                    binding.rv.layoutManager = GridLayoutManager(context,2)
                    adapter.setOnItemClickListener(object : CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(context, BookDetail::class.java)
                            intent.putExtra("id",arrBooks[position]._id)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<GetBooksResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }
}