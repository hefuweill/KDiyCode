package com.hefuwei.kdiycode.util

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Activity的管理类，可以finish所有Activity，避免出现应用重启日志消失的情况
 */
object ActivityCollector {

    private const val TAG = "ActivityCollector"

    private val activityList = ArrayList<WeakReference<Activity>?>()

    fun size(): Int {
        return activityList.size
    }

    fun add(weakRefActivity: WeakReference<Activity>?) {
        activityList.add(weakRefActivity)
    }

    fun remove(weakRefActivity: WeakReference<Activity>?) {
        activityList.remove(weakRefActivity)
    }

    fun finishAll() {
        if (activityList.isNotEmpty()) {
            for (activityWeakReference in activityList) {
                val activity = activityWeakReference?.get()
                if (activity != null && !activity.isFinishing) {
                    activity.finish()
                }
            }
            activityList.clear()
        }
    }
}