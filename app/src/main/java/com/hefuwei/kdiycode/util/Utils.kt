package com.hefuwei.kdiycode.util

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.hefuwei.kdiycode.BuildConfig
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.DiyCodeApplication
import com.hefuwei.kdiycode.Global
import java.io.File
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ShareUtils {

    companion object {
        /**
         * 保存boolean类型的键值对到SP中
         */
        fun save(key: String, value: Boolean) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        /**
         * 保存float类型的键值对到SP中
         */
        fun save(key: String, value: Float) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.putFloat(key, value)
            editor.apply()
        }

        /**
         * 保存int类型的键值对到SP中
         */
        fun save(key: String, value: Int) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.putInt(key, value)
            editor.apply()
        }

        /**
         * 保存long类型的键值对到SP中
         */
        fun save(key: String, value: Long) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.putLong(key, value)
            editor.apply()
        }

        /**
         * 保存string类型的键值对到SP中
         */
        fun save(key: String, value: String) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.putString(key, value)
            editor.apply()
        }

        /**
         * 读取与传入键对应的boolean类型的值
         */
        fun read(key: String, defValue: Boolean): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.getBoolean(key, defValue)
        }

        /**
         * 读取与传入键对应的float类型的值
         */
        fun read(key: String, defValue: Float): Float {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.getFloat(key, defValue)
        }

        /**
         * 读取与传入键对应的int类型的值
         */
        fun read(key: String, defValue: Int): Int {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.getInt(key, defValue)
        }

        /**
         * 读取与传入键对应的long类型的值
         */
        fun read(key: String, defValue: Long): Long {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.getLong(key, defValue)
        }

        /**
         * 读取与传入键对应的string类型的值
         */
        fun read(key: String, defValue: String): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.getString(key, defValue)
        }

        /**
         * 判断SP是否包含某个键
         */
        operator fun contains(key: String): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(DiyCode.app)
            return prefs.contains(key)
        }

        /**
         * 清除SP中的某个键
         */
        fun clear(key: String) {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.remove(key)
            editor.apply()
        }

        /**
         * 清除SP中的所有键
         */
        fun clearAll() {
            val editor = PreferenceManager.getDefaultSharedPreferences(
                    DiyCode.app).edit()
            editor.clear()
            editor.apply()
        }
    }
}

class LogUtils {

    companion object {

        private val DEBUG = BuildConfig.DEBUG

        fun d(msg: String?) {
            if (DEBUG) {
                Log.d(Global.LOG_TAG, msg)
            }
        }

        fun e(msg: String?) {
            if (DEBUG) {
                Log.e(Global.LOG_TAG, msg)
            }
        }
    }
}

class FileUtils {

    companion object {
        fun getCacheDir(): File {
            val externalCacheDir = DiyCode.app.externalCacheDir
            return externalCacheDir ?: DiyCode.app.cacheDir
        }
    }
}

class UIUtils {

    companion object {

        private val mainH: Handler = Handler(Looper.getMainLooper()) { true }

        fun getString(resId: Int) = DiyCode.app.getString(resId)!!

        fun getDrawable(resId: Int) = DiyCode.app.getDrawable(resId)!!

        fun getColor(resId: Int) = DiyCode.app.resources.getColor(resId)

        fun getVersion() = DiyCode.app.packageManager.getPackageInfo(DiyCode.app.packageName, 0).versionCode

        fun runOnUIThread(runnable: Runnable) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                mainH.post(runnable)
            } else {
                runnable.run()
            }
        }

        fun showShortToast(msg: String?) {
            Toast.makeText(DiyCode.app, msg, Toast.LENGTH_SHORT).show()
        }

        fun showShortToast(resId: Int) {
            Toast.makeText(DiyCode.app, getString(resId), Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(msg: String?) {
            Toast.makeText(DiyCode.app, msg, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(resId: Int) {
            Toast.makeText(DiyCode.app, getString(resId), Toast.LENGTH_LONG).show()
        }

        fun dp2px(i: Int): Int = (i * DiyCode.app.resources.displayMetrics.density).toInt()


        fun getHost(url: String) = URL(url).host!!

        /**
         * 解决部分图片无法显示的bug
         */
        fun replaceLargeAvatarToAvatar(source: String): String {
            return source.replace("large_avatar", "avatar")
        }

        /**
         * 格式化服务端返回的事件
         */
        fun formatTime(oldDateStr: String): String? {
            val shortString: String
            val time = dealDateFormat(oldDateStr)
            val date = getDateByString(time) ?: return null
            val now = Calendar.getInstance().timeInMillis
            val delTime = (now - date.time) / 1000
            shortString = when {
                delTime > 365 * 24 * 60 * 60 -> "${(delTime / (365 * 24 * 60 * 60)).toInt()}年前"
                delTime > 24 * 60 * 60 -> "${(delTime / (24 * 60 * 60)).toInt()}天前"
                delTime > 60 * 60 -> "${(delTime / (60 * 60)).toInt()}小时前"
                delTime > 60 -> "${(delTime / 60).toInt()}分前"
                delTime > 1 -> "${delTime}秒前"
                else -> "1秒前"
            }
            return shortString
        }

        @SuppressLint("SimpleDateFormat")
        private fun dealDateFormat(oldDateStr: String): String {
            try {
                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val date = df.parse(oldDateStr)
                val df1 = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK)
                val date1 = df1.parse(date.toString())
                val df2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return df2.format(date1)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun getDateByString(time: String?): Date? {
            var date: Date? = null
            if (time == null) {
                return null
            }
            val dateFormat = "yyyy-MM-dd HH:mm:ss"
            val format = SimpleDateFormat(dateFormat)
            try {
                date = format.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }
    }
}