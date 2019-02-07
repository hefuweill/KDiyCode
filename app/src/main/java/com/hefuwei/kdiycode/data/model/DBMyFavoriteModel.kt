package com.hefuwei.kdiycode.data.model

import org.litepal.crud.LitePalSupport

/**
 * 与DBLoginUserInfoModel进行多对多关联
 */
data class DBMyFavoriteModel(val url: String, var title: String, var time: Long,
                             val users: ArrayList<DBLoginUserInfoModel>?) : LitePalSupport() {
    var id: Long = 0
}