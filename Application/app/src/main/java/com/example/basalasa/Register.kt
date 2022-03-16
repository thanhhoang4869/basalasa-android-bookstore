package com.example.basalasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Register : AppCompatActivity() {
    private var registerNavLogin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //find by id
        registerNavLogin = findViewById(R.id.registerNavLogin)

        //event
        registerNavLogin!!.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}