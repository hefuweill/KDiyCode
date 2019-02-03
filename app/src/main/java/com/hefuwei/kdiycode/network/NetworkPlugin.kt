package com.hefuwei.kdiycode.network

import com.hefuwei.kdiycode.BuildConfig
import com.hefuwei.kdiycode.Global
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkPlugin {

    val diyApi: DiyApi

    init {
        val builder = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
        builder.addInterceptor(AppendTokenInterceptor())
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        val mRetrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Global.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        diyApi = mRetrofit.create(DiyApi::class.java)
    }

}