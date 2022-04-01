package com.example.basalasa.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.basalasa.R
import com.example.basalasa.databinding.ActivitySettingChangePasswordBinding
import com.example.basalasa.model.body.ChangePasswordBody
import com.example.basalasa.model.reponse.ChangePasswordResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import com.example.basalasa.utils.SHA256.Companion.sha256
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingChangePassword : AppCompatActivity() {
    private lateinit var binding: ActivitySettingChangePasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_password)
        binding = ActivitySettingChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.changePasswordChangeBtn.setOnClickListener {
            changePassword(this)
        }
    }

    private fun changePassword(context: Context) {
        val oldPass = binding.changePasswordOldPass.text.toString().sha256()
        val newPass = binding.changePasswordNewPass.text.toString().sha256()
        val rePass = binding.changePasswordRePass.text.toString().sha256()


        if(newPass == rePass) {
            val token = context.let { Cache.getToken(it) }
            val response = token?.let { MyAPI.getAPI().changePass(it, ChangePasswordBody(oldPass, newPass))}

            response?.enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data?.exitcode == 0) {
                            Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show()
                            finish()
                        } else if (data?.exitcode == 400) {
                            Toast.makeText(context, "Current password is incorrect", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                    t.printStackTrace();
                }
            })
        } else Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
    }
}