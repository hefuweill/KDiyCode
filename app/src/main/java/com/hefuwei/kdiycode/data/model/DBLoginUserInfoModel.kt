package com.hefuwei.kdiycode.data.model

import org.litepal.crud.LitePalSupport

/**
 * 与DBMyFavorite进行多对多关联
 */
data class DBLoginUserInfoModel(val username: String, val password: String, val uid: Int,
                                var favorites: ArrayList<DBMyFavoriteModel>?) : LitePalSupport() {
    var id: Long = 0
}