package com.example.basalasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basalasa.R
import com.example.basalasa.adapter.CategoryAdapter
import com.example.basalasa.databinding.FragmentCategoryBinding
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.reponse.GetBooksResponse
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var arrBooks:ArrayList<Book>
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
        loadListBook()

    }
    fun loadListBook(){
        val response = MyAPI.getAPI().getBooks()
        arrBooks = ArrayList()

        response.enqueue(object : Callback<GetBooksResponse> {
            override fun onResponse(call: Call<GetBooksResponse>, response: Response<GetBooksResponse>) {
                System.out.println("ALOOOO")
                if (response.isSuccessful) {
                    val data = response.body()

                    for(item: Book in data!!.arrBook!!) {
                        arrBooks.add(item)
                        System.out.println("ON "+item.toString())
                    }

                    //bind to adapter
                    binding.rvCategoryListItem!!.adapter = CategoryAdapter(arrBooks)
                    binding.rvCategoryListItem!!.layoutManager = LinearLayoutManager(context)
                }
            }
            override fun onFailure(call: Call<GetBooksResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace();
            }




        })
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