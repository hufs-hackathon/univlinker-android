package com.practice.farmate_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.play.integrity.internal.j
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.ActivityDetail
import com.practice.farmate_android.data.ActivityItem
import com.practice.farmate_android.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val boardId = intent.getLongExtra("boardId", 0)

        if (boardId != 0L) {
            RetrofitClient.createRetorfitClient().watchActivity(boardId)
                .enqueue(object : Callback<ActivityDetail> {
                    override fun onResponse(
                        call: Call<ActivityDetail>,
                        response: Response<ActivityDetail>
                    ) {
                        Glide.with(this@DetailActivity)
                            .load(response.body()?.imageUrl)
                            .into(binding.ivThumbnail)

                        binding.tvTag.text = response.body()?.tag
                        binding.tvTitle.text = response.body()?.title
                        binding.tvDetail.text = response.body()?.content
                    }

                    override fun onFailure(call: Call<ActivityDetail>, t: Throwable) {

                    }

                })
        }


    }
}