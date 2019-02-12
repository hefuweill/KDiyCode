package com.hefuwei.kdiycode.pages.main.fragment.sites

import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.data.model.SectionEntityModel
import com.hefuwei.kdiycode.pages.main.fragment.projects.SitesContract
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class SitesPresenter(val view: SitesContract.View) : SitesContract.Presenter {

    private var disposable: Disposable? = null
    val dataList = ArrayList<SectionEntityModel>()

    override fun subscribe() {
        sites()
    }

    override fun unsubscribe() {
        disposable.safelyDispose()
    }


    private fun sites() {
        disposable = DataRepository.sites()
                .subscribe({
                    for (i in it) {
                        dataList.add(SectionEntityModel(true, i.name))
                        for (j in i.sites) {
                            dataList.add(SectionEntityModel(j))
                        }
                    }
                    view.notifyDataSetChanged()
                }, {
                    view.notifyDataLoadFail()
                })
    }

    override fun onRefresh() {
        dataList.clear()
        sites()
    }

}