package com.xayah.acmezone.Class

import org.litepal.crud.LitePalSupport

class ClassTieBaAccount : LitePalSupport() {
    lateinit var userAgent: String
    lateinit var BDUSS: String
    lateinit var cookie: String
    lateinit var name: String
    lateinit var concern_num: String
    lateinit var fans_num: String
    lateinit var like_forum_num: String
    lateinit var post_num: String
    lateinit var portrait_url: String

    var signed_forum_num = "?"
    var state = "unknown"
    var signedTime = ""



}