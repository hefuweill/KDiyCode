package com.hefuwei.kdiycode.network

import com.hefuwei.kdiycode.Auth
import com.hefuwei.kdiycode.DiyCode
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 给每个请求带上Auth请求头
 */
class AppendTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = DiyCode.token
        if (!token.isNullOrEmpty()) {
            val authHeader = Auth.TOKEN_PREFIX + DiyCode.token
            request = request.newBuilder()
                    .addHeader(Auth.KEY_TOKEN, authHeader)
                    .build()
        }
        return chain.proceed(request)
    }

}