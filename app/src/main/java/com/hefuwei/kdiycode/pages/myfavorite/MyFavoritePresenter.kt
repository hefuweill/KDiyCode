package com.hefuwei.kdiycode.pages.myfavorite

import com.hefuwei.kdiycode.data.DataRepository
import com.hefuwei.kdiycode.data.model.DBMyFavoriteModel
import com.hefuwei.kdiycode.util.safelyDispose
import io.reactivex.disposables.Disposable

class MyFavoritePresenter(val view: MyFavoriteContract.View) : MyFavoriteContract.Presenter {

    private var disposables = ArrayList<Disposable?>()
    val favorites = ArrayList<DBMyFavoriteModel>()

    override fun subscribe() {
        getMyFavorite()
    }

    override fun unsubscribe() {
        disposables.safelyDispose()
    }

    override fun deleteFavorite(position: Int) {
        disposables.add(DataRepository.deleteUserFavorites(favorites[position])
                .subscribe({
                    if (it) {
                        favorites.removeAt(position)
                    }
                    view.notifyDeleteResult(it)
                }, {}))
    }

    private fun getMyFavorite() {
        disposables.add(DataRepository.currentUserFavorites()
                .subscribe({ favorites.clear(); favorites.addAll(it); view.notifyDataSetChanged() }, {}))
    }
}