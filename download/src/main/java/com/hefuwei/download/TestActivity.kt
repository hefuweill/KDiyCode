package com.hefuwei.download

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hefuwei.download.R.layout.activity_test
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executors

class TestActivity : AppCompatActivity() {

    var binder: DownloadService.MBinder? = null
    private lateinit var tv: TextView
    private val address = "http://evideo.souche.com/a.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        val intent = Intent(this, DownloadService::class.java)
        startService(Intent(this, DownloadService::class.java))
        bindService(intent, object: ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                binder = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is DownloadService.MBinder) {
                    binder = service
                }
            }
        }, Context.BIND_AUTO_CREATE)
        setContentView(activity_test)
        tv = findViewById(R.id.tv_download)
    }

    fun startDownload(view: View) {
        binder?.startDownload(address)
    }

    fun pauseDownload(view: View) {
        binder?.pauseDownload(address)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(event: DownloadEvent) {
        when(event.type) {
            DownloadType.FAIL -> Toast.makeText(this, "下载失败 原因：${event.reason}",
                    Toast.LENGTH_SHORT).show()
            DownloadType.PROGRESS_CHANGE -> tv.text = "${event.currentDownloadLength}/${event.totalLength}"
            DownloadType.PAUSE -> tv.text = "${tv.text}暂停"
            DownloadType.SUCCESS -> tv.text = "下载成功"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}