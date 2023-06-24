package com.practice.farmate_android.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.JoinUserInfo
import com.practice.farmate_android.databinding.ActivitySigninInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninInfoActivity: AppCompatActivity() {
    var school = "서울대학교"
    var major = "경영학부"
    lateinit var binding: ActivitySigninInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin_info)

        val sharedPreferencesManager = SharedPreferencesManager.getInstance(this)

        val schoolSpinner = binding.spinnerSchool
        val majorSpinner = binding.spinnerMajor

        val schoolSpinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.school,
            android.R.layout.simple_spinner_item
        )

        val majorSpinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.major,
            android.R.layout.simple_spinner_item
        )

        // 드롭다운 시 레이아웃 설정
        schoolSpinnerAdapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        majorSpinnerAdapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        // address(spinner 뷰)에 만들어놓은 adapter를 할당한다.
        schoolSpinner.adapter = schoolSpinnerAdapter
        schoolSpinner.dropDownVerticalOffset = 120
        majorSpinner.adapter = majorSpinnerAdapter
        majorSpinner.dropDownVerticalOffset = 120



        schoolSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                school = when (position) {
                    0 -> {
                        "서울대학교"
                    }
                    1 -> {
                        "고려대학교"
                    }
                    2 -> {
                        "연세대학교"
                    }
                    3 -> {
                        "경희대학교"
                    }
                    else -> {
                        "한국외국어대학교"
                    }
                }
                Log.d("선택된 관심지역", school)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않는 경우는 발생하지 않으므로 비워둠
            }
        }


        majorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                major = when (position) {
                    0 -> {
                        "경영학부"
                    }
                    1 -> {
                        "경제학부"
                    }
                    2 -> {
                        "정보통신공학과"
                    }
                    3 -> {
                        "전자공학과"
                    }
                    else -> {
                        "산업경영공학과"
                    }
                }
                Log.d("선택된 관심지역", major)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않는 경우는 발생하지 않으므로 비워둠
            }
        }

        val name = sharedPreferencesManager.getString("name", "null")
        val email = sharedPreferencesManager.getString("email", "null")
        val profileUrl = sharedPreferencesManager.getString("profileUrl", "null")
        val role = sharedPreferencesManager.getInt("role", 0)

        Log.d("info", "${name}, ${email}, ${profileUrl}, ${role}")

        if (name != "null" && email != "null" && profileUrl != "null") {
            Log.d("충족", "성공")
            RetrofitClient.createRetorfitClient().join(JoinUserInfo(email, name, major, school, role, profileUrl))
                .enqueue(object : Callback<Long?> {
                    override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                        if (response.code() == 200) {
                            val userId = response.body()
                            if (userId != null) {
                                sharedPreferencesManager.saveLong("userId", userId)
                                Log.d("회원가입 성공", "${response.code()}")
                                val intent = Intent(this@SigninInfoActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Log.d("회원가입 실패", "${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                        Log.d("회원가입 실패", "${t.message}")
                    }
                })
        } else {
            Log.d("충족", "실패")
        }

    }
}