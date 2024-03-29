package com.practice.farmate_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.farmate_android.R
import com.practice.farmate_android.databinding.ActivityMentorDetailBinding

class MentorDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMentorDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mentor_detail)
    }
}