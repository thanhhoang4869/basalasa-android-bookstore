package com.example.basalasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Login : AppCompatActivity() {
    private var loginNavRegister: TextView? = null
    private var loginNavForgestPassword: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //find by id
        loginNavRegister = findViewById(R.id.loginNavRegister)
        loginNavForgestPassword = findViewById(R.id.loginNavForgestPassword)

        //event
        loginNavRegister!!.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        loginNavForgestPassword!!.setOnClickListener {
            startActivity(Intent(this, ForgetPassword::class.java))
        }
    }
}