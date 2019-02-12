package com.hefuwei.download

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface API {

    @Headers("Connection:close")
    @Streaming
    @GET
    fun downloadFile(@Url path: String, @Header("Range") range: String): Observable<ResponseBody>

}