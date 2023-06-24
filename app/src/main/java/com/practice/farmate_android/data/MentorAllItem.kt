package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class MentorAllItem (
    @SerializedName("boardId") val boardId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("major") val major: String,
    @SerializedName("isMentor") val isMentor: Int
)