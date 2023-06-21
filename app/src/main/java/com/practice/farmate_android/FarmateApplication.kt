package com.practice.farmate_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class FarmateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, applicationContext.resources.getString(R.string.KAKAO_NATIVE_APP_KEY))
    }
}