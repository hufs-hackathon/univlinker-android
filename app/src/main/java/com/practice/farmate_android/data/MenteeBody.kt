package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class MenteeBody(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("category") val category: String
)
