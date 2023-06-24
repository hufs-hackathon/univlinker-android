package com.practice.farmate_android.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.SocialLogin
import com.practice.farmate_android.databinding.ActivitySocialLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialLoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySocialLoginBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)

        sharedPreferencesManager = SharedPreferencesManager.getInstance(this)
        // 테스트용 sp 삭제하기
//        sharedPreferencesManager.deleteString()


        binding.btnKakao.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.d(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.d(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.d(ContentValues.TAG, "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.d(ContentValues.TAG, "사용자 정보 요청 성공")
                            val kakaoEmail = user?.kakaoAccount?.email.toString()
                            val kakaoProfileUrl = user?.kakaoAccount?.profile?.profileImageUrl.toString()
                            val kakaoName = user?.kakaoAccount?.profile?.nickname.toString()
                            Log.d("카카오 이름", kakaoName)
                            Log.d("카카오 이메일", kakaoEmail)
                            Log.d("카카오 프로필", kakaoProfileUrl)
                            sharedPreferencesManager.saveString("email", kakaoEmail)
                            sharedPreferencesManager.saveString("name", kakaoName)
                            sharedPreferencesManager.saveString("profileUrl", kakaoProfileUrl)
                            // 서버에 토큰요청
                            RetrofitClient.createRetorfitClient().loginKakao(SocialLogin(kakaoEmail, kakaoName, kakaoProfileUrl))
                                .enqueue(object : Callback<Long?> {
                                    override fun onResponse(
                                        call: Call<Long?>,
                                        response: Response<Long?>
                                    ) {
                                        if (response.code() == 200 && response.body() != null) {
                                            val userId = response.body()
                                            if (userId != null) {
                                                if (userId == 0L) {
                                                    val intent = Intent(this@SocialLoginActivity, SigninSelectActivity::class.java)
                                                    startActivity(intent)
                                                } else {
                                                    Log.d("로그인 성공", "${response.code()}")
                                                    sharedPreferencesManager.saveLong("userId", userId)
                                                    val intent = Intent(this@SocialLoginActivity, MainActivity::class.java)
                                                    startActivity(intent)
                                                }

                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                                        Log.d("카카오 로그인 실패", "${t.message}")
                                    }

                                })

                        }
                    }
                }
            }
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                Log.d("카카오톡 설치 확인", "설치")
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    Log.d("카카오톡 앱", "진입")
                    if (error != null) {
                        Log.d("카카오톡으로 로그인 실패", "${error}")
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.d("카카오톡 로그인", "취소")
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.d(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.d(ContentValues.TAG, "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.d(ContentValues.TAG, "사용자 정보 요청 성공")
                                val kakaoEmail = user?.kakaoAccount?.email.toString()
                                val kakaoProfileUrl = user?.kakaoAccount?.profile?.profileImageUrl.toString()
                                val kakaoName = user?.kakaoAccount?.profile?.nickname.toString()
                                Log.d("카카오 이름", kakaoName)
                                Log.d("카카오 이메일", kakaoEmail)
                                Log.d("카카오 프로필", kakaoProfileUrl)
                                sharedPreferencesManager.saveString("email", kakaoEmail)
                                sharedPreferencesManager.saveString("name", kakaoName)
                                sharedPreferencesManager.saveString("profileUrl", kakaoProfileUrl)

                                // 서버에 토큰 요청
                                RetrofitClient.createRetorfitClient().loginKakao(SocialLogin(kakaoEmail, kakaoName, kakaoProfileUrl))
                                    .enqueue(object : Callback<Long?> {
                                        override fun onResponse(
                                            call: Call<Long?>,
                                            response: Response<Long?>
                                        ) {
                                            if (response.code() == 200 && response.body() != null) {
                                                val userId = response.body()
                                                if (userId != null) {
                                                    if (userId == 0L) {
                                                        val intent = Intent(this@SocialLoginActivity, SigninSelectActivity::class.java)
                                                        startActivity(intent)
                                                    } else {
                                                        Log.d("로그인 성공", "${response.code()}")
                                                        sharedPreferencesManager.saveLong("userId", userId)
                                                        val intent = Intent(this@SocialLoginActivity, MainActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<Long?>, t: Throwable) {
                                            Log.d("카카오 로그인 실패", "${t.message}")
                                        }

                                    })

                            }
                        }
                    }
                }
            } else {
                Log.d("카카오톡 설치 확인", "미설치")
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                Log.d("카카오 로그인 절차 - 카카오 계정 로그인", "종료")
            }
            Log.d("카카오톡 로그인", "종료")
        }
    }
}