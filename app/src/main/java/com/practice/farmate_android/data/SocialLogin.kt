package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class SocialLogin(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("imageUrl") val imageUrl: String
)

data class JoinUserInfo(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("major") val major: String,
    @SerializedName("univ") val univ: String,
    @SerializedName("role") val role: Int,
    @SerializedName("imageUrl") val imageUrl: String
)


