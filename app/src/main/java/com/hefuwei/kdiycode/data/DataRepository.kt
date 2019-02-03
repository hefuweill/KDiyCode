package com.hefuwei.kdiycode.data

import com.hefuwei.kdiycode.Auth
import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.network.NetworkPlugin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 所有跟数据操作相关的操作，请求网络、缓存、读取数据库等
 */
class DataRepository {

    companion object {

        private val diyApi = NetworkPlugin.diyApi

        fun init() = diyApi.init()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun login(name: String, password: String) = diyApi.login(Global.CLIENT_ID,
                Global.CLIENT_SECRET, Auth.AUTH_TYPE, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!
    }
}
