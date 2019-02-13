package com.hefuwei.kdiycode.pages.uploadimg

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.bumptech.glide.Glide
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.pages.choosepic.ChoosePicActivity
import com.hefuwei.kdiycode.pages.choosepic.PreviewPicActivity
import com.hefuwei.kdiycode.pages.myfavorite.MyFavoritePresenter
import com.hefuwei.kdiycode.util.UIUtils
import kotlinx.android.synthetic.main.activity_upload_img.*
import java.io.File

class UploadImgActivity : BaseActivity(), UploadImgContract.View {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rl_add)
    lateinit var rlAdd: RelativeLayout
    @BindView(R.id.iv)
    lateinit var iv: ImageView
    @BindView(R.id.ll_info)
    lateinit var llInfo: LinearLayout
    @BindView(R.id.tv_link)
    lateinit var tvLink: TextView
    @BindView(R.id.tv_copy)
    lateinit var tvCopy: TextView
    private var imgPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_img)
        presenter = UploadImagePresenter(this)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.title = getString(R.string.upload_pic)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setupEvent() {
        super.setupEvent()
        rlAdd.setOnClickListener {
            ChoosePicActivity.actionStart(this)
        }
        tvCopy.setOnClickListener {
            val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.text = tvLink.text.toString().substringAfterLast("：")
            UIUtils.showShortToast(getString(R.string.copy_success))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_upload_img, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> finish()
                R.id.upload -> {
                    if (imgPath.isNullOrEmpty()) {
                        UIUtils.showShortToast(getString(R.string.please_add_pic))
                    } else {
                        // 执行上传逻辑
                        (presenter as UploadImagePresenter).uploadImage(File(imgPath))
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            imgPath = data.getStringExtra(PreviewPicActivity.PATH)
            iv.visibility = View.VISIBLE
            rlAdd.visibility = View.GONE
            Glide.with(iv)
                    .load(imgPath)
                    .into(iv)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun notifyUploadSuccess(url: String) {
        UIUtils.showShortToast("上传成功")
        llInfo.visibility = View.VISIBLE
        tv_link.text = "链接为：$url"
    }

    override fun notifyUploadFail() {
    }

    companion object {
        fun actionStart(ctx: Context) {
            ctx.startActivity(Intent(ctx, UploadImgActivity::class.java))
        }
    }
}