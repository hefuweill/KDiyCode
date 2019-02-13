package com.hefuwei.kdiycode.pages.uploadimg

import com.hefuwei.kdiycode.data.DataRepository
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadImagePresenter(val view: UploadImgContract.View) : UploadImgContract.Presenter {

    private var disposable: Disposable? = null

    override fun uploadImage(file: File) {
        val body = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, body)
        disposable = DataRepository.uploadImg(part)
                .subscribe({
                    if (it is Map<*, *>) {
                        view.notifyUploadSuccess(it[("image_url")] as String)
                    }
                }, {view.notifyUploadFail()})
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

}