package com.hefuwei.kdiycode.network

import com.hefuwei.kdiycode.data.model.LoginModel
import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DiyApi {

    /**
     * 闪屏页调用，让服务端知道当前设备还活着，客户端不关心返回值
     */
    @POST("devices.json")
    fun init(): Observable<String>

    /**
     * 在退出应用时调用，服务器会删除token
     */
    @DELETE("devices.json")
    fun logout()

    @FormUrlEncoded
    @POST("/oauth/token")
    fun login(@Field("client_id") client_id: String, @Field("client_secret") client_secret: String,
                          @Field("grant_type") grant_type: String, @Field("username") username: String,
                          @Field("password") password: String): Observable<LoginModel>
}