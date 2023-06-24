package com.practice.farmate_android.ui

import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.FaqItem
import com.practice.farmate_android.data.MentorBody
import com.practice.farmate_android.databinding.ActivityWriteMentorBinding
import com.practice.farmate_android.ui.adapter.FaqRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteMentorActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWriteMentorBinding
    var isTitle = false
    var isIntro = false
    var interest = "취업"
    var itemList = ArrayList<FaqItem>()
    var faqNum = 0
    var titleStr: String = ""
    var introStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_mentor)

        val faqRVAdapter = FaqRVAdapter(this, itemList)

        val userId = SharedPreferencesManager.getInstance(this).getLong("userId", 0L)

        binding.editTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    isTitle = true
                    titleStr = s.toString()
                } else {
                    isTitle = false
                    titleStr = s.toString()
                }
                Log.d("isTitle", "${isTitle}")
                if (isTitle == true && isIntro == true) {
                    binding.btnUploadActive.visibility = View.VISIBLE
                    binding.btnUploadInactive.visibility = View.INVISIBLE
                } else {
                    binding.btnUploadActive.visibility = View.INVISIBLE
                    binding.btnUploadInactive.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.editIntro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    isIntro = true
                    introStr = s.toString()
                } else {
                    isIntro = false
                    introStr = s.toString()
                }
                Log.d("isIntro", "${isIntro}")

                if (isTitle == true && isIntro == true) {
                    binding.btnUploadActive.visibility = View.VISIBLE
                    binding.btnUploadInactive.visibility = View.INVISIBLE
                } else {
                    binding.btnUploadActive.visibility = View.INVISIBLE
                    binding.btnUploadInactive.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        if (binding.chipVocation.isChecked) {
            interest = "취업"
        } else if (binding.chipDeveloper.isChecked) {
            interest = "개발"
        } else {
            interest = "언어"
        }
        Log.d("checked", "$interest")

        binding.chipGroupInterests.setOnCheckedStateChangeListener { group, checkedIds ->
            if (binding.chipVocation.isChecked) {
                interest = "취업"
            } else if (binding.chipDeveloper.isChecked) {
                interest = "개발"
            } else {
                interest = "언어"
            }

            Log.d("checked", "$interest")
        }

        binding.tvAddFaq.setOnClickListener {
            faqNum++
            if (faqNum == 1) {
                binding.layoutFaq1.visibility = View.VISIBLE
                itemList.add(FaqItem("", ""))
            } else if (faqNum == 2) {
                binding.layoutFaq2.visibility = View.VISIBLE
                itemList.add(FaqItem("", ""))
            } else if (faqNum == 3) {
                binding.layoutFaq3.visibility = View.VISIBLE
                binding.tvAddFaq.visibility = View.INVISIBLE
                itemList.add(FaqItem("", ""))
            }
        }

        binding.editQuestion1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[0].question = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editQuestion2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[1].question = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editQuestion3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[2].question = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editAnswer1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[0].answer = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editAnswer2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[1].answer = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editAnswer2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemList[1].answer = s.toString()
                Log.d("list", itemList.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.btnUploadActive.setOnClickListener {
            RetrofitClient.createRetorfitClient().postMentor(userId, MentorBody(titleStr, introStr, interest, itemList))
                .enqueue(object : Callback<Long?> {
                    override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                        Log.d("성공", "${response.code()}")
                        finish()
                    }

                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                        Log.d("실패", "${t.message}")
                    }

                })
        }
    }
}