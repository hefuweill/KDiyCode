package com.hefuwei.kdiycode.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 全局的异常处理，写文件，上传服务器
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private var mContext: Application? = null
    private var mDefaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var mPath: String? = null
    private const val PREFIX = "crash"
    private const val SUFFIX = "log"

    fun initialize(context: Context) {
        // 始终拿的都是Application的Context
        mContext = context.applicationContext as Application?
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        mPath = Environment.getExternalStorageDirectory().toString() + "/" + mContext!!.packageName
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            dumpExceptionToSDCard(e)
            uploadExceptionToServer()
            ActivityCollector.finishAll()
            if (mDefaultUncaughtExceptionHandler != null) {
                mDefaultUncaughtExceptionHandler!!.uncaughtException(t, e)
            }
        } catch (exception: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 导出异常到SD卡
     */
    @SuppressLint("SimpleDateFormat")
    @Throws(Exception::class)
    private fun dumpExceptionToSDCard(e: Throwable) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            return
        }
        val dir = File(mPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val logFile = File(mPath, "$PREFIX$time.$SUFFIX")
        val pw = PrintWriter(BufferedWriter(FileWriter(logFile)))
        pw.println(time)
        dumpPhoneInfo(pw)
        e.printStackTrace(pw)
        pw.close()
    }

    @Throws(PackageManager.NameNotFoundException::class)
    private fun dumpPhoneInfo(pw: PrintWriter) {
        //应用的版本名称和版本号
        val pm = mContext!!.packageManager
        val pi = pm.getPackageInfo(mContext!!.packageName, PackageManager
                .GET_ACTIVITIES)
        pw.print("App Version: ")
        pw.print(pi.versionName)
        pw.print('_')
        pw.println(pi.versionCode)

        //android版本号
        pw.print("OS Version: ")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)

        //手机制造商
        pw.print("Vendor: ")
        pw.println(Build.MANUFACTURER)

        //手机型号
        pw.print("Model: ")
        pw.println(Build.MODEL)

        //cpu架构
        pw.print("CPU ABI: ")
        pw.println(Build.CPU_ABI)
    }

    private fun uploadExceptionToServer() {}
}