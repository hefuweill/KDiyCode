package com.hefuwei.kdiycode.util

import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.hefuwei.kdiycode.BuildConfig
import com.hefuwei.kdiycode.DiyCode
import com.hefuwei.kdiycode.Global

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

class UIUtils {

    companion object {

        fun getString(resId: Int) = DiyCode.app.getString(resId)

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
    }
}