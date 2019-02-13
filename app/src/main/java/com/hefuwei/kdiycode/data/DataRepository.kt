package com.hefuwei.kdiycode.data

import com.hefuwei.kdiycode.Auth
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.DiyCode.Companion.uid
import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.data.model.DBLoginUserInfoModel
import com.hefuwei.kdiycode.data.model.DBMyFavoriteModel
import com.hefuwei.kdiycode.network.NetworkPlugin
import com.hefuwei.kdiycode.util.LogUtils
import com.hefuwei.kdiycode.util.removeElementIf
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import org.litepal.LitePal
import java.util.*
import kotlin.collections.ArrayList

/**
 * 所有跟数据操作相关的操作，请求网络、缓存、读取数据库等
 */
class DataRepository {

    companion object {

        private val diyApi = NetworkPlugin.diyApi

        fun saveLoginUserInfo(username: String, password: String, uid: Int): Observable<Boolean> {
            val observable = Observable.create { emitter: Emitter<Boolean> ->
                val userList = LitePal.findAll(DBLoginUserInfoModel::class.java)
                val userInfo = userList.find { it.uid == uid }
                if (userInfo == null) {
                    // 执行插入操作
                    LogUtils.d("insert userInfo username: $username, password: $password")
                    emitter.onNext(DBLoginUserInfoModel(username, password, uid, null).save())
                } else {
                    emitter.onNext(true)
                }
                emitter.onComplete()
            }
            return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())!!
        }

        fun addMyFavorite(title: String, url: String): Observable<Boolean> {
            val observable = Observable.create { emitter: Emitter<Boolean> ->
                val currentUser = LitePal.findAll(DBLoginUserInfoModel::class.java, true)
                        .find { it.uid == uid }!!
                val favorites = currentUser.favorites!!
                if (favorites.size > 0) {
                    var favorite = favorites.find { it.title == title }
                    if (favorite != null) {
                        // 只更新下time和title,由于favorite里面不含User列表，所以需要再查一次
                        favorite = LitePal.find(DBMyFavoriteModel::class.java, favorite.id, true)
                        favorite.time = Date().time
                        favorite.title = title
                        favorite.save()
                    } else {
                        // 没有的情况那么就添加，先判断下当前链接数据库是否存在
                        val dbFavorite = LitePal.findAll(DBMyFavoriteModel::class.java)
                                .find { it.url == url }
                        if (dbFavorite == null) {
                            // 数据库里也没有代表所有用户都没收藏
                            val waitInsertFavorite = DBMyFavoriteModel(url, title, Date().time, arrayListOf(currentUser))
                            waitInsertFavorite.save()
                            favorites.add(waitInsertFavorite)
                            currentUser.save()
                        } else {
                            // 数据库里面已经有该链接了，意味着别的用户收藏了
                            dbFavorite.users!!.add(currentUser)
                            dbFavorite.save()
                            currentUser.favorites!!.add(dbFavorite)
                        }
                    }
                } else {
                    // 看看数据库里面有没有
                    val dbFavorite = LitePal.findAll(DBMyFavoriteModel::class.java)
                            .find { it.url == url }
                    if (dbFavorite == null) {
                        // 数据库里也没有代表所有用户都没收藏
                        val waitInsertFavorite = DBMyFavoriteModel(url, title, Date().time, arrayListOf(currentUser))
                        waitInsertFavorite.save()
                        currentUser.favorites = arrayListOf(waitInsertFavorite)
                        currentUser.save()
                    } else {
                        // 数据库里面已经有该链接了，意味着别的用户收藏了
                        dbFavorite.users!!.add(currentUser)
                        dbFavorite.save()
                        currentUser.favorites = arrayListOf(dbFavorite)
                        currentUser.save()
                    }
                }
                emitter.onNext(true)
            }
            return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())!!
        }

        fun currentUserFavorites() = Observable.create { emitter: Emitter<ArrayList<DBMyFavoriteModel>> ->
                val currentUser = LitePal.findAll(DBLoginUserInfoModel::class.java, true)
                        .find { it.uid == uid }!!
                if (currentUser.favorites == null) {
                    emitter.onNext(arrayListOf())
                } else {
                    emitter.onNext(currentUser.favorites!!)
                }
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

        fun deleteUserFavorites(favoriteModel: DBMyFavoriteModel) = Observable.create { emitter: Emitter<Boolean> ->
            val users = favoriteModel.users
            if (users != null && users.size > 0) {
                emitter.onNext(users.removeElementIf { it.uid == uid })
            } else if (users != null && users.size == 0) {
                // 直接删除数据库里面的记录
                if (favoriteModel.isSaved) {
                    emitter.onNext(favoriteModel.delete() != 0)
                } else {
                    emitter.onNext(false)
                }
            } else {
                emitter.onNext(false)
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

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

        fun sites() = diyApi.sites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

        fun uploadImg(file: MultipartBody.Part) = diyApi.uploadImage(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!
    }
}
