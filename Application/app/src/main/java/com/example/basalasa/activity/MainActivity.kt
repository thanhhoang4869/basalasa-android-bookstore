package com.example.basalasa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import retrofit2.*
import androidx.fragment.app.Fragment
import com.example.basalasa.fragment.CategoryFragment
import com.example.basalasa.fragment.HomeFragment
import com.example.basalasa.fragment.ProfileFragment
import com.example.basalasa.fragment.SettingsFragment
import com.example.basalasa.R
import com.example.basalasa.model.LoginResponse
import com.example.basalasa.utils.MyAPI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val categoryFragment = CategoryFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(homeFragment)

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_settings->setCurrentFragment(settingsFragment)
//                R.id.menu_settings->{
//                    val intent = Intent(this, Login::class.java)
//                    startActivity(intent)
//                    finish()
//                }
                R.id.menu_profile->setCurrentFragment(profileFragment)
                R.id.menu_home->setCurrentFragment(homeFragment)
                R.id.menu_category->setCurrentFragment(categoryFragment)
            }
            true
        }


//        val response = MyAPI.getAPI().getLogin()
//        response.enqueue(object: Callback<LoginResponse> {
//            override fun onResponse(
//                call: Call<LoginResponse>,
//                response: Response<LoginResponse>
//            ) {
//
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        Log.d("log",data.data)
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Log.d("log","Teo")
//            }
//        })
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}