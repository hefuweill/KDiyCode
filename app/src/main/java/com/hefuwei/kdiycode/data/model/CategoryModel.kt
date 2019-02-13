package com.hefuwei.kdiycode.data.model

/**
 * 选择图片目录的model
 */
data class CategoryModel(val name: String, val parentPath: String) {
    var count = 1
    var path = ""
    var checked = false
}