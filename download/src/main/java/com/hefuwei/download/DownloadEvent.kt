package com.hefuwei.download

class DownloadEvent(var type: DownloadType) {
    var totalLength: Long = 0
    var currentDownloadLength: Long = 0
    var reason = ""
}