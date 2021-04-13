package com.xayah.acmezone.Class

import org.litepal.crud.LitePalSupport

class ClassBilibiliAccount : LitePalSupport() {
    lateinit var userAgent: String
    lateinit var DedeUserID: String
    lateinit var bili_jct: String
    lateinit var SESSDATA: String
    lateinit var cookie: String
    lateinit var current_level: String
    lateinit var current_exp: String
    lateinit var next_exp: String
    lateinit var money: String
    lateinit var bcoin_balance: String
    lateinit var vipStatus: String
    lateinit var uname: String
    lateinit var face: String
    var signedTime = ""


    var stateLogin = "unknown"
    var stateWatch = "unknown"
    var stateShare = "unknown"
    var stateThrow = "unknown"
    var stateSilver2Coin = "unknown"
    var stateLiveSign = "unknown"

    var tasksLogin = "YES"
    var tasksWatch = "YES"
    var tasksShare = "YES"
    var tasksThrow = "YES"
    var tasksSilver2Coin = "YES"
    var tasksLiveSign = "YES"
    var liveSignReward = "你可能已经签到过了哦，今天无法获取签到奖励了呢~"

    var isSpecifyVideo = "NO"
    var specifyVideoBVID = ""
    var specifyVideoPartition = "4"
    var throwPartition = "4"
    var throwNum = "5"


}