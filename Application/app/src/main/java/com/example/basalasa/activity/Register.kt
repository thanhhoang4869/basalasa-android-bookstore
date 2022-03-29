package com.example.basalasa.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.basalasa.R
import com.example.basalasa.model.LoginBody
import com.example.basalasa.model.LoginResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private var registerNavLogin: TextView? = null
    private var registerSignUpButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //find by id
        registerNavLogin = findViewById(R.id.registerNavLogin)
        registerSignUpButton = findViewById((R.id.registerSignUpBtn))

        //event
        registerNavLogin!!.setOnClickListener {
            finish()
        }

        registerSignUpButton!!.setOnClickListener {

        }
    }

//    private fun checkAccount(context: Context) {
//        val usernameStr = findViewById<EditText>(R.id.register)
//        val passwordStr =
//
//        val response = MyAPI.getAPI().postLogin(LoginBody(usernameStr, passwordStr))
//
//        response.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data?.exitcode == 0) {
//                        Intent(context, MainActivity::class.java).also {
//                            startActivity(it)
//                            finish()
//                        }
//
//                    } else if (data?.exitcode == 104) {
//                        Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
//                t.printStackTrace();
//            }
//        })
//    }
}