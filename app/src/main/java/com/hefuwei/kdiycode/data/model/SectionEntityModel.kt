package com.hefuwei.kdiycode.data.model

import com.chad.library.adapter.base.entity.SectionEntity

class SectionEntityModel : SectionEntity<SiteModel.SitesBean> {

    constructor(sitesBean: SiteModel.SitesBean) : super(sitesBean)

    constructor(isHeader: Boolean, header: String?) : super(isHeader, header)
}