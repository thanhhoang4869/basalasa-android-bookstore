package com.example.basalasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgetPassword : AppCompatActivity() {
    private var forgetBackBtn: Button? = null
    private var forgetSendBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //find by ID
        forgetBackBtn = findViewById(R.id.forgetBackBtn)
        forgetSendBtn = findViewById(R.id.forgetSendBtn)

        forgetBackBtn!!.setOnClickListener {
            finish()
        }
    }
}