package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class Mypage(
    @SerializedName("userId") val userId: Long,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("major") val major: String,
    @SerializedName("univ") val univ: String,
    @SerializedName("role") val role: Int
)
