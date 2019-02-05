package com.hefuwei.kdiycode.data

import com.hefuwei.kdiycode.Auth
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.network.NetworkPlugin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Field

/**
 * 所有跟数据操作相关的操作，请求网络、缓存、读取数据库等
 */
class DataRepository {

    companion object {

        private val diyApi = NetworkPlugin.diyApi

        fun init() = diyApi.init(Global.PLATFORM, DiyCode.token!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun login(name: String, password: String) = diyApi.login(Global.CLIENT_ID,
                Global.CLIENT_SECRET, Auth.AUTH_TYPE, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun me() = diyApi.me()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun newsList(node_id: Int, offset: Int, limit: Int) = diyApi.newsList(node_id, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun userInfo(login: String) = diyApi.userInfo(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun nodeList() = diyApi.nodeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun createNews(title: String, address: String, id: Int) = diyApi.createNews(title, address, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!
    }
}
