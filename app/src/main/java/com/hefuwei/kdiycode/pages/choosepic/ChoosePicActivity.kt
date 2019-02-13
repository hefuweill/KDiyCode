package com.hefuwei.kdiycode.pages.choosepic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListPopupWindow
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.common.BaseActivity
import com.hefuwei.kdiycode.util.UIUtils

/**
 * 选择照片
 */
class ChoosePicActivity : BaseActivity(), ChoosePicContract.View {

    @BindView(R.id.rv)
    lateinit var rv: RecyclerView
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    lateinit var adapter: ChoosePicAdapter
    private lateinit var popAdapter: BaseAdapter
    private lateinit var choosePicPresenter: ChoosePicPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_pic)
        presenter = ChoosePicPresenter(this)
        choosePicPresenter = presenter as ChoosePicPresenter
    }

    override fun setupViews() {
        super.setupViews()
        adapter = ChoosePicAdapter(R.layout.item_choose_img, choosePicPresenter.dataList)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
    }

    override fun setupEvent() {
        super.setupEvent()
        adapter.setOnItemClickListener { _, view, position ->
            val iv = view.findViewById<ImageView>(R.id.iv)
            iv.transitionName = UIUtils.getString(R.string.image)
            PreviewPicActivity.actionStart(this, iv,
                    choosePicPresenter.dataList[position].path)
        }
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbar.title = UIUtils.getString(R.string.choose_pic)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choose_pic, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.filter -> showChooseCategoryPop()
                android.R.id.home -> finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showChooseCategoryPop() {
        val pop = ListPopupWindow(this)
        popAdapter = object : BaseAdapter() {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                var cv = convertView
                if (cv == null) {
                    cv = LayoutInflater.from(parent?.context).
                            inflate(R.layout.item_pop_choose_category, parent, false)
                }
                val iv = cv!!.findViewById<ImageView>(R.id.iv)
                val tvTitle = cv.findViewById<TextView>(R.id.tv_title)
                val tvCount = cv.findViewById<TextView>(R.id.tv_count)
                val ivCheck = cv.findViewById<ImageView>(R.id.iv_check)
                val model = choosePicPresenter.categoryList[position]
                Glide.with(iv)
                        .load(model.path)
                        .into(iv)
                tvTitle.text = model.name
                tvCount.text = "${model.count}张"
                if (model.checked) {
                    ivCheck.visibility = View.VISIBLE
                } else {
                    ivCheck.visibility = View.INVISIBLE
                }
                cv.setOnClickListener {
                    for (i in choosePicPresenter.categoryList) {
                        i.checked = false
                    }
                    model.checked = true
                    if (position == 0) {
                        choosePicPresenter.changeDir("")
                    } else {
                        choosePicPresenter.changeDir(model.parentPath)
                    }
                    pop.dismiss()
                }
                return cv
            }

            override fun getItem(position: Int): Any = choosePicPresenter.categoryList[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getCount() = choosePicPresenter.categoryList.size
        }
        pop.setAdapter(popAdapter)
        pop.setContentWidth(window.decorView.width)
        pop.width = -1
        pop.height = -1
        pop.isModal = true
        pop.anchorView = (window.decorView as ViewGroup).getChildAt(1)
        pop.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val intent = Intent()
            intent.putExtra(PreviewPicActivity.PATH, data.getStringExtra(PreviewPicActivity.PATH))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        fun actionStart(ctx: Activity) {
            val intent = Intent(ctx, ChoosePicActivity::class.java)
            ctx.startActivityForResult(intent, 0)
        }
    }
}