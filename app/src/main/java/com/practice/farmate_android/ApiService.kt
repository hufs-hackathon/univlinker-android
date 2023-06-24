package com.practice.farmate_android

import android.app.appsearch.SearchResult
import com.practice.farmate_android.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // TODO : API Request 전송 방식 협의하기

    // 1. Login & Sign up

    // 카카오 로그인
//    @FormUrlEncoded
//    @POST ("/login")
//    fun loginKakao(
//        @Field("profileUrl") profileUrl: String,
//        @Field("email") email: String
//    ): Call<LoginResponse>

    @POST("/login")
    fun loginKakao(
        @Body loginBody: SocialLogin
    ): Call<Long?>

    @POST("/join")
    fun join(
        @Body joinBody: JoinUserInfo
    ): Call<Long?>


    // 2
    // 멘토 게시글 조회
    @GET("/univlinker/mentor/all")
    fun getMentorServices(
        @Query("userId") userId: Long,
        @Query("category") category: String
    ): Call<List<MentorAllItem>>

    // 멘토 게시글 작성
    @POST("/univlinker/mentor")
    fun postMentor(
        @Query("userId") userId: Long,
        @Body mentorBody: MentorBody
    ): Call<Long?>

    // 멘티 게시글 작성
    @Multipart
    @POST("univlinker/mentee")
    fun postMentee(
        @Query("userId") userId: Long,
        @Part("menteeBody") menteeBody: MenteeBody,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<Long?>

    @GET("/univlinker/board/all")
    fun getAllData(
        @Query("userId") userId: Long
    ): Call<List<AllActivities>>

    @GET("/mypage")
    fun getMyPage(
        @Query("userId") userId: Long
    ): Call<Mypage>

    @GET("/univlinker/board")
    fun watchActivity(
        @Query("boardId") boardId: Long
    ): Call<ActivityDetail>
}