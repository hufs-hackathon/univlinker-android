package com.practice.farmate_android.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.FaqItem
import com.practice.farmate_android.data.MenteeBody
import com.practice.farmate_android.data.MentorBody
import com.practice.farmate_android.databinding.ActivityWriteMenteeBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class WriteMenteeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWriteMenteeBinding
    private val GALLERY_REQUEST_CODE = 201
    private var imageUri: Uri? = null
    private lateinit var photoURI: Uri
    private lateinit var file: String
    private var imageUriString: String = ""
    val pictureList = ArrayList<String>()
    val imageUriList = ArrayList<String>()
    var isTitle = false
    var isIntro = false
    var interest = "취업"
    var itemList = ArrayList<FaqItem>()
    var faqNum = 0
    var titleStr = ""
    var contentStr = ""
    private var imagePartList = ArrayList<MultipartBody.Part>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = SharedPreferencesManager.getInstance(this).getLong("userId", 0L)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_mentee)

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

        binding.editQuestion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    isIntro = true
                    contentStr = s.toString()
                } else {
                    isIntro = false
                    contentStr = s.toString()
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

        binding.btnUploadActive.setOnClickListener {

            val media = ArrayList<MultipartBody.Part>()
            val jsonObject = JSONObject(
                "{\"title\":\"${titleStr}\", " +
                        "\"content\":\"${contentStr}\", " +
                        "\"category\":\"${interest}\"}"
            ).toString()

            val jsonBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())

            Log.d("게시글 업로드 json body", jsonBody.toString())


            RetrofitClient.createRetorfitClient().postMentee(userId, MenteeBody(titleStr, contentStr, interest), media)
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