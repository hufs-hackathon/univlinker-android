package com.practice.farmate_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.farmate_android.R
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.databinding.ActivitySigninSelectBinding

class SigninSelectActivity: AppCompatActivity() {
    lateinit var binding: ActivitySigninSelectBinding
    var status = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin_select)

        if (binding.radioMento.isChecked) {
            status = 0
        } else if (binding.radioMentee.isChecked) {
            status = 1
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (binding.radioMento.isChecked) {
                status = 0
            } else if (binding.radioMentee.isChecked) {
                status = 1
            }
        }

        binding.btnNextActive.setOnClickListener {
            val intent = Intent(this, SigninInfoActivity::class.java)
            SharedPreferencesManager.getInstance(this)
                .saveInt("role", status)
            startActivity(intent)
        }
    }
}