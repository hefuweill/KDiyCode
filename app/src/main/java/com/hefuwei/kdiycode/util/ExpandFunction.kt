package com.hefuwei.kdiycode.util

import io.reactivex.disposables.Disposable

fun Disposable?.safelyDispose() {
    if (this != null && !this.isDisposed) {
        this.dispose()
    }
}