package com.hefuwei.kdiycode.pages.choosepic

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.hefuwei.kdiycode.R
import com.hefuwei.kdiycode.data.model.CategoryModel
import com.hefuwei.kdiycode.data.model.ImageModel
import com.hefuwei.kdiycode.util.UIUtils
import java.io.File

class ChoosePicPresenter(val view: ChoosePicContract.View) : ChoosePicContract.Presenter {

    val dataList = ArrayList<ImageModel>()
    val categoryList = ArrayList<CategoryModel>()
    private lateinit var loaderManager: LoaderManager
    private lateinit var loaderCallback: LoaderManager.LoaderCallbacks<Cursor>

    override fun subscribe() {
        loadImages()
    }

    override fun unsubscribe() {

    }

    override fun changeDir(path: String) {
        // 表明选择了全部
        if (path.isEmpty()) {
            loaderManager.destroyLoader(LOAD_CATEGORY)
            loaderManager.restartLoader(LOAD_IMAGE, null, loaderCallback)
        } else {
            val args = Bundle()
            args.putString(PATH, path)
            loaderManager.destroyLoader(LOAD_IMAGE)
            loaderManager.restartLoader(LOAD_CATEGORY, args, loaderCallback)
        }
    }

    private fun loadImages() {
        loaderManager = LoaderManager.getInstance(view as AppCompatActivity)
        loaderCallback = object: LoaderManager.LoaderCallbacks<Cursor> {

            /**
             * 需要这几个数据
             */
            private val IMAGE_PROJECTION = arrayOf(MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media._ID)

            override fun onLoaderReset(loader: Loader<Cursor>) {
            }

            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return if (id == LOAD_IMAGE) {
                    CursorLoader(view as AppCompatActivity,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_PROJECTION, null, null,
                            IMAGE_PROJECTION[2] + " DESC")
                } else {
                    // 加载指定目录下面的图片
                    CursorLoader(view as AppCompatActivity,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                            IMAGE_PROJECTION[0] + " like '%" + args?.getString("path") + "%'",
                            null, IMAGE_PROJECTION[2] + " DESC")
                }
            }

            override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                // 必须加上判断，不然从大图回来又会加载界面，导致动画不对
                if (data != null && data.count > 0 && data.count != dataList.size) {
                    val hasFolder = categoryList.size != 0
                    dataList.clear()
                    data.moveToPosition(-1)
                    while (data.moveToNext()) {
                        val path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                        val name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))
                        val time = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
                        dataList.add(ImageModel(path, name, time))
                        if (!hasFolder) {
                            // 只加载一次目录
                            val firstFile = File(path)
                            val model = CategoryModel(firstFile.parentFile.name, firstFile.parentFile.path)
                            model.path = firstFile.path
                            if (!categoryList.contains(model)) {
                                categoryList.add(model)
                            } else {
                                categoryList[categoryList.indexOf(model)].count++
                            }
                        }
                    }
                    if (!hasFolder) {
                        // 添加所有图片
                        var allPicCount = 0
                        for (i in categoryList) {
                            allPicCount += i.count
                        }
                        val model = CategoryModel(UIUtils.getString(R.string.all_pic), "")
                        model.path = categoryList[0].path
                        model.count = allPicCount
                        categoryList.add(0, model)
                    }
                    view.notifyDataSetChanged()
                }
            }
        }
        loaderManager.initLoader(LOAD_IMAGE, null, loaderCallback)
    }


    companion object {
        const val LOAD_IMAGE = 1
        const val LOAD_CATEGORY = 2
        const val PATH = "path"
    }

}