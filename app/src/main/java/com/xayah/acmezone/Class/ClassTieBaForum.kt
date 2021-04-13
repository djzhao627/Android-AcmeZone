package com.xayah.acmezone.Class

import org.litepal.crud.LitePalSupport

class ClassTieBaForum : LitePalSupport() {
    lateinit var account_name: String
    lateinit var fid: String
    lateinit var name: String
    lateinit var cur_score: String
    lateinit var level_id: String
    lateinit var levelup_score: String
    var state = "unknown"

}