package com.hefuwei.kdiycode.pages.choosepic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import butterknife.BindView
import com.bumptech.glide.Glide
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.util.UIUtils

class PreviewPicActivity : BaseActivity() {

    @BindView(R.id.iv)
    lateinit var iv: ImageView
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_pic)
    }

    override fun setupViews() {
        super.setupViews()
        path = intent.getStringExtra(PATH)
        Glide.with(iv)
                .load(path)
                .into(iv)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.title = getString(R.string.see_large_pic)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_preview, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId) {
                R.id.bt_check -> {
                    val intent = Intent()
                    intent.putExtra(PATH, path)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                android.R.id.home -> {
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val PATH = "path"

        fun actionStart(ctx: Activity, view: View, path: String) {
            val intent = Intent(ctx, PreviewPicActivity::class.java)
            intent.putExtra(PATH, path)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(ctx, Pair.create(view,
                    UIUtils.getString(R.string.image)))
            ctx.startActivityForResult(intent, 0, options.toBundle())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}