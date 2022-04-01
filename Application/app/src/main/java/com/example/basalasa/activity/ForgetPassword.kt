package com.example.basalasa.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.basalasa.R
import com.example.basalasa.model.body.ForgetBody
import com.example.basalasa.model.reponse.ForgetResponse
import com.example.basalasa.utils.MyAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        forgetSendBtn!!.setOnClickListener {
            forgetPassword(this)
        }
    }

    private fun forgetPassword(context: Context) {
        val email = findViewById<EditText>(R.id.forgetEmailEditText).text.toString()

        val response = MyAPI.getAPI().postForgetPassword(ForgetBody(email))

        response.enqueue(object : Callback<ForgetResponse> {
            override fun onResponse(
                call: Call<ForgetResponse>,
                response: Response<ForgetResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        Intent(context, Login::class.java).also {
                            Toast.makeText(
                                context,
                                "Please check your email for new password",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(it)
                            finish()
                        }
                    } else if (data?.exitcode == 500) {
                        Toast.makeText(
                            context,
                            "Fail to send mail, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<ForgetResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace();
            }
        })
    }
}