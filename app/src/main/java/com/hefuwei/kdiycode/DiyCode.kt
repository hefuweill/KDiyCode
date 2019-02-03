package com.hefuwei.kdiycode

import android.app.Application
import com.hefuwei.kdiycode.data.model.LoginModel
import com.hefuwei.kdiycode.util.LogUtils
import com.hefuwei.kdiycode.util.ShareUtils
import java.util.*

class DiyCode {

    companion object {

        var isLogin = false
        var token: String? = null
        lateinit var app: Application

        /**
         * 初始化操作，在Application.onCreate处调用，传入Application实例
         */
        fun initialize(application: Application) {
            app = application
            refreshLoginState()
        }

        fun refreshLoginState() {
            val isTokenExpired = isTokenExpired()
            if (!isTokenExpired) {
                token = ShareUtils.read(Auth.TOKEN, "")
                isLogin = true
            } else {
                token = null
                isLogin = false
            }
        }

        private fun isTokenExpired(): Boolean {
            val createdAt = ShareUtils.read(Auth.CREATE_AT, 0)
            val expiresIn = ShareUtils.read(Auth.EXPIRES_IN, 0)
            val currentTime = Date().time / 1000
            return expiresIn - (currentTime - createdAt) / 1000 <= 0
        }
    }
}