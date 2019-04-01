package com.hefuwei.kdiycode.data

import com.hefuwei.kdiycode.Global
import com.hefuwei.kdiycode.util.FileUtils
import com.hefuwei.kdiycode.util.UIUtils
import com.jakewharton.disklrucache.DiskLruCache


object DiskCacheManager {

    private val mCache: DiskLruCache = DiskLruCache.open(FileUtils.getCacheDir(), UIUtils.getVersion(),
            1, Global.CACHE_MAX_SIZE)

    fun size() = mCache.size()

    fun edit(key: String) = mCache.edit(key)

    fun isClose() = mCache.isClosed

    fun flush() {
        mCache.flush()
    }

    fun delete() {
        mCache.delete()
    }

    fun save() {

    }

}