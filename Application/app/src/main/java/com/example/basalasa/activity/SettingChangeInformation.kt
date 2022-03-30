package com.example.basalasa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.example.basalasa.R

class SettingChangeInformation : AppCompatActivity() {
    var changeInfoSaveBtn: Button? = null
    var changeInfoFullNameEditText: EditText? = null
    var changeInfoPhoneEditText: EditText? = null
    var changeInfoAddressEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_change_information)

        changeInfoSaveBtn = findViewById(R.id.changeInfoSaveBtn)
        changeInfoFullNameEditText = findViewById(R.id.changeInfoFullNameTextEdit)
        changeInfoPhoneEditText = findViewById(R.id.changeInfoPhoneTextEdit)
        changeInfoAddressEditText = findViewById(R.id.changeInfoAddressTextEdit)


        changeInfoSaveBtn!!.setOnClickListener {

        }
    }
}