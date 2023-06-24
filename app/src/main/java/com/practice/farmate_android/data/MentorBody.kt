package com.practice.farmate_android.data

import com.google.gson.annotations.SerializedName

data class MentorBody(
    @SerializedName("title") val title: String,
    @SerializedName("introduce") val introduce: String,
    @SerializedName("category") val category: String,
    @SerializedName("faqList") val faqList: ArrayList<FaqItem>
)

data class Faq(
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String
)
