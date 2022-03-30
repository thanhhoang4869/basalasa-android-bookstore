package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.basalasa.R

class SettingChangePassword : AppCompatActivity() {
    var changePasswordChangeBtn: Button? = null
    var changePasswordOldPass: EditText? = null
    var changePasswordNewPass: EditText? = null
    var changePasswordRePass: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_password)

        changePasswordChangeBtn = findViewById(R.id.changePasswordChangeBtn)
        changePasswordOldPass = findViewById(R.id.changePasswordOldPass)
        changePasswordNewPass = findViewById(R.id.changePasswordNewPass)
        changePasswordRePass = findViewById(R.id.changePasswordRePass)

        changePasswordChangeBtn!!.setOnClickListener {
            //to do
        }
    }
}