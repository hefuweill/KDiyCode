package com.hefuwei.download

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.RandomAccessFile
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 不使用IntentService的原因是其只有一个子线程，无法并行下载多个文件
 */
class DownloadService : Service() {

    /**
     * 下载路径
     */
    private lateinit var downloadPath: String

    /**
     * 使用一个缓存线程池，用于下载
     */
    private val executor = Executors.newCachedThreadPool()

    private val disposables = ConcurrentHashMap<String, Disposable>()

    override fun onBind(intent: Intent?): IBinder? {
        return MBinder()
    }

    override fun onCreate() {
        super.onCreate()
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw RuntimeException("请挂载外部存储！")
        } else {
            downloadPath = "${Environment.getExternalStorageDirectory()}/$packageName"
        }
    }

    fun startDownload(address: String) {
        if (disposables[address] == null) {
            // 只有还没下载才下载
            executor.execute(DownloadRunner(address))
        }
    }

    fun pauseDownload(address: String) {
        Log.d("ppp", disposables.toString())
        if (disposables[address] != null) {
            disposables[address]!!.dispose()
            val event = DownloadEvent(DownloadType.PAUSE)
            EventBus.getDefault().post(event)
            disposables.remove(address)
        }
    }

    companion object {
        const val POSTFIX = "downloading"
        val api = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .build())
                .baseUrl("https://www.baidu.com/")
                .build()
                .create(API::class.java)

    }

    inner class DownloadRunner(private val address: String) : Runnable {
        override fun run() {
            // 步骤一，检测目标文件是否已经存在，若存在表示已经下载完了(那么不再进行后续步骤)
            val event = DownloadEvent(DownloadType.SUCCESS)
            val targetFileName = "${MD5.md5(address)}.${address.substringAfterLast(".")}"
            val targetFile = File(downloadPath, targetFileName)
            if (targetFile.exists()) {
                // 如果已经下载完了，发送一个下载完成的消息
                event.totalLength = targetFile.length()
                event.currentDownloadLength = event.totalLength
                EventBus.getDefault().post(event)
                return
            }
            // 步骤二，计算range，如果临时文件不存在那么就重新下载，不然就从临时文件的位置开始下载
            var range = "bytes=0-"
            val tempFile = File(downloadPath, "$targetFileName.$POSTFIX")
            if (tempFile.exists()) {
                range = "bytes=${tempFile.length()}-"
            }
            api.downloadFile(address, range)
                    .subscribe(object: Observer<ResponseBody> {
                        override fun onComplete() {
                        }
                        override fun onSubscribe(d: Disposable) {
                            disposables[address] = d
                        }
                        override fun onNext(t: ResponseBody) {
                            writeFile(t, tempFile, targetFile)
                        }
                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            event.type = DownloadType.FAIL
                            event.reason = e.message!!
                            EventBus.getDefault().post(event)
                            disposables.remove(address)
                        }

                    })
        }
        private fun writeFile(body: ResponseBody, tempFile: File, targetFile: File) {
            // 步骤三，创建随机读取文件，若不使用会覆盖文件
            val randomAccessFile = RandomAccessFile(tempFile, "rw")
            val event = DownloadEvent(DownloadType.PROGRESS_CHANGE)
            // 步骤四，如果临时文件存在那么就定位到临时文件的末尾继续下载
            if (tempFile.exists()) {
                randomAccessFile.seek(tempFile.length())
                event.totalLength = tempFile.length() + body.contentLength()
                event.currentDownloadLength = tempFile.length()
            } else {
                event.totalLength = body.contentLength()
                event.currentDownloadLength = 0
            }
            // 步骤五，发送一个正在下载的消息
            EventBus.getDefault().post(event)
            val bytes = ByteArray(1024 * 1000)
            val inputStream = body.byteStream()
            var length = inputStream.read(bytes)
            var totalSize = 0
            while (length != -1) {
                randomAccessFile.write(bytes, 0, length)
                event.currentDownloadLength += length
                // 步骤六，每次下载1k都发送一下消息
                EventBus.getDefault().post(event)
                length = inputStream.read(bytes)
                totalSize += length
            }
            // 步骤七，当下载完成后重命名文件，并且发送一个下载完成的消息
            if (targetFile.exists()) {
                targetFile.delete()
            }
            tempFile.renameTo(targetFile)
            event.type = DownloadType.SUCCESS
            event.currentDownloadLength = event.totalLength
            EventBus.getDefault().post(event)
            disposables.remove(address)
        }
    }

    inner class MBinder: Binder() {
        fun startDownload(address: String) {
            this@DownloadService.startDownload(address)
        }

        fun pauseDownload(address: String) {
            this@DownloadService.pauseDownload(address)
        }
    }
}

enum class DownloadType {

    // 进度改变，会告知总长度和当前进度
    PROGRESS_CHANGE,
    // 做为客户端暂停命令的回执
    PAUSE,
    // 下载失败
    FAIL,
    // 下载完成
    SUCCESS
}