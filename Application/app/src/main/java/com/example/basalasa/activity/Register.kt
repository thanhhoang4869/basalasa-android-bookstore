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
import com.example.basalasa.model.body.RegisterBody
import com.example.basalasa.model.reponse.RegisterResponse
import com.example.basalasa.utils.MyAPI
import com.example.basalasa.utils.SHA256.Companion.sha256
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
            registerAccount(this)
        }
    }

    private fun registerAccount(context: Context) {
        val email = findViewById<EditText>(R.id.registerEmailEditText).text.toString()
        val password = findViewById<EditText>(R.id.registerPasswordEditText).text.toString()
        val rePassword = findViewById<EditText>(R.id.registerRetypePasswordEditText).text.toString()
        val fullName = findViewById<EditText>(R.id.registerFullNameEditText).text.toString()
        val phone = findViewById<EditText>(R.id.registerPhoneEditText).text.toString()
        val address = findViewById<EditText>(R.id.registerAddressEditText).text.toString()

        if(!password.equals(rePassword)) {
            Toast.makeText(context, "Retype password does not match", Toast.LENGTH_LONG).show()
        } else {
            val response = MyAPI.getAPI().postRegister(RegisterBody(email, password.sha256(), fullName, phone, address))

            response.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data?.exitcode == 0) {
                            Intent(context, Login::class.java).also {
                                Toast.makeText(context, "Please activate your account", Toast.LENGTH_LONG).show()
                                startActivity(it)
                                finish()
                            }

                        } else if (data?.exitcode == 701 ) {
                            Toast.makeText(context, "Email has been already used", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace();
                }
            })
        }
    }
}