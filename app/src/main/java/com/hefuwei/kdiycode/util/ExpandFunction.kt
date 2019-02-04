package com.hefuwei.kdiycode.util

import io.reactivex.disposables.Disposable

fun Disposable?.safelyDispose() {
    if (this != null && !this.isDisposed) {
        this.dispose()
    }
}

fun ArrayList<Disposable?>.safelyDispose() {
    for (i in this) {
        if (i != null && !i.isDisposed) {
            i.dispose()
        }
    }
}