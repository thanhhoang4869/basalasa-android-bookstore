package com.example.basalasa.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.basalasa.R
import com.example.basalasa.databinding.ActivityMainBinding
import com.example.basalasa.fragment.CategoryFragment
import com.example.basalasa.fragment.HomeFragment
import com.example.basalasa.fragment.SettingsFragment
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val homeFragment = HomeFragment()
        val categoryFragment = CategoryFragment()
        val settingsFragment = SettingsFragment()

        binding.topNavBar.isVisible = true
        setCurrentFragment(homeFragment)

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_settings -> {
                    processSettings(this, settingsFragment)
                }
                R.id.menu_home -> {
                    binding.topNavBar.isVisible = true
                    setCurrentFragment(homeFragment)
                }
                R.id.menu_category -> {
                    binding.topNavBar.isVisible = true
                    setCurrentFragment(categoryFragment)
                }
            }
            true
        }

        val searchBar = findViewById<EditText>(R.id.searchBar)
        searchBar.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val intent = Intent(this, SearchResults::class.java)
                intent.putExtra("searchInput", searchBar.text.toString())
                startActivity(intent)
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
        binding.cartBtn.setOnClickListener {
            val token = Cache.getToken(this)
            System.out.println(token)
            if (token == null) {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Cart::class.java)
                startActivity(intent)
            }

        }
    }

    private fun processSettings(context: Context, fragment: Fragment) {
        val token = Cache.getToken(context)
        if (token === null) {
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            setCurrentFragment(fragment)
            binding.topNavBar.isVisible = false
        }

//        val response = MyAPI.getAPI().getAccount(token.toString())

//        response.enqueue(object : Callback<GetAccountResponse> {
//            override fun onResponse(
//                call: Call<GetAccountResponse>,
//                response: Response<GetAccountResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data?.exitcode == 0) {
//                        setCurrentFragment(fragment)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<GetAccountResponse>, t: Throwable) {
//                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
//                t.printStackTrace()
//            }
//        })
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}