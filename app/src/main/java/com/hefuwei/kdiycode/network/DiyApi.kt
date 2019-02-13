package com.hefuwei.kdiycode.network

import com.hefuwei.kdiycode.data.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface DiyApi {

    /**
     * 闪屏页调用，让服务端知道当前设备还活着，客户端不关心返回值
     */
    @FormUrlEncoded
    @POST("devices.json")
    fun init(@Field("platform") platform: String, @Field("token") token: String): Observable<Any>

    /**
     * 在退出应用时调用，服务器会删除token
     */
    @DELETE("devices.json")
    fun logout()

    @GET("users/me.json")
    fun me(): Observable<UserInfoModel>

    @FormUrlEncoded
    @POST("../../oauth/token")
    fun login(@Field("client_id") client_id: String, @Field("client_secret") client_secret: String,
                          @Field("grant_type") grant_type: String, @Field("username") username: String,
                          @Field("password") password: String): Observable<LoginModel>

    @GET("news.json")
    fun newsList(@Query("node_id") node_id: Int, @Query("offset") offset: Int,
                    @Query("limit") limit: Int): Observable<List<NewsModel>>

    @GET("users/{login}.json")
    fun userInfo(@Path("login") login: String): Observable<UserInfoModel>

    @GET("news/nodes.json")
    fun nodeList(): Observable<List<NodeModel>>

    @FormUrlEncoded
    @POST("news.json")
    fun createNews(@Field("title") title: String, @Field("address") address: String,
                   @Field("node_id") id: Int): Observable<NewsModel>

    @GET("sites.json")
    fun sites(): Observable<List<SiteModel>>

    @Multipart
    @POST("/api/v3/photos.json")
    fun uploadImage(@Part file: MultipartBody.Part): Observable<Any>
}