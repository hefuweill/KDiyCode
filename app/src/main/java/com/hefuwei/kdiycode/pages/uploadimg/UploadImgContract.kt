package com.hefuwei.kdiycode.pages.uploadimg

import com.hefuwei.kdiycode.common.BasePresenter
import com.hefuwei.kdiycode.common.BaseView
import java.io.File

interface UploadImgContract {

    interface View : BaseView<Presenter> {
        fun notifyUploadSuccess(url: String)
        fun notifyUploadFail()
    }

    interface Presenter : BasePresenter {
        fun uploadImage(file: File)
    }
}