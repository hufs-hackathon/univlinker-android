package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class AllActivities(
    @SerializedName("category") val category: String,
    @SerializedName("activityBoardResponseDtoList") val dtoList: List<EachActivity>
)

data class EachActivity(
    @SerializedName("title") val title: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("tag") val tag: String,
    @SerializedName("boardId") val boardId: Long
)