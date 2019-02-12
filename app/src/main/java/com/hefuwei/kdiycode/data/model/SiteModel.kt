package com.hefuwei.kdiycode.data.model

class SiteModel(val name: String, val id: Int, val sites: List<SitesBean>) {


    /**
     * sites : [{"name":"Gratisography","url":"http://www.gratisography.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/www.gratisography.com.ico"},{"name":"Unsplash","url":"http://unsplash.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/unsplash.com.ico"},{"name":"Publicdomainarchive","url":"http://publicdomainarchive.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/publicdomainarchive.com.ico"},{"name":"pixabay","url":"http://pixabay.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/pixabay.com.ico"},{"name":"pexels","url":"http://www.pexels.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/www.pexels.com.ico"},{"name":"peakpx","url":"http://www.peakpx.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/www.peakpx.com.ico"},{"name":"pixite source","url":"http://source.pixite.co","avatar_url":"https://favicon.b0.upaiyun.com/ip2/source.pixite.co.ico"},{"name":"泼辣有图","url":"http://www.polayoutu.com/collections","avatar_url":"https://diycode.b0.upaiyun.com/site/2017/703bb38696fa400114f3f5802362bef9.png"},{"name":"visualhunt","url":"http://visualhunt.com","avatar_url":"https://diycode.b0.upaiyun.com/site/2017/8d723702fa1d85c94f8e61e066646dbc.png"},{"name":"artstation","url":"http://www.artstation.com","avatar_url":"https://favicon.b0.upaiyun.com/ip2/www.artstation.com.ico"}]
     * name : Free high-resolution pictures WebSites － 免费高清无版权图片素材网站
     * id : 21
     */

    class SitesBean {
        /**
         * name : Gratisography
         * url : http://www.gratisography.com
         * avatar_url : https://favicon.b0.upaiyun.com/ip2/www.gratisography.com.ico
         */

        var name: String? = null
        var url: String? = null
        var avatar_url: String? = null
    }
}
