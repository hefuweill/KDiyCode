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

fun <T> ArrayList<T>.removeElementIf(lambda: (T) -> Boolean): Boolean {
    var removed = false
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val data = iterator.next()
        if (lambda(data)) {
            iterator.remove()
            removed = true
        }
    }
    return removed
}