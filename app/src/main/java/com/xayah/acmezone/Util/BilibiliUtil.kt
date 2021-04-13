package com.xayah.acmezone.Util

import com.xayah.acmezone.Class.ClassBilibiliAccount
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.litepal.LitePal
import org.litepal.extension.find
import java.io.IOException

class BilibiliUtil {

    companion object {

        private val rewardApi = "https://api.bilibili.com/x/member/web/exp/reward"

        private val loginApi = "https://api.bilibili.com/x/web-interface/nav"

        private val videoGetApi =
            "https://api.bilibili.com/x/web-interface/dynamic/region?rid="

        private val videoHeartbeatApi = "https://api.bilibili.com/x/click-interface/web/heartbeat"

        private val videoShareApi = "https://api.bilibili.com/x/web-interface/share/add"

        private val throwCoinApi = "https://api.bilibili.com/x/web-interface/coin/add"

        private val mangaApi = "https://manga.bilibili.com/twirp/activity.v1.Activity/ClockIn"

        private val silver2CoinApi = "https://api.live.bilibili.com/pay/v1/Exchange/silver2coin"

        private val silver2CoinStatusApi = "https://api.live.bilibili.com/pay/v1/Exchange/getStatus"

        private val liveSignApi = "https://api.live.bilibili.com/xlive/web-ucenter/v1/sign/DoSign"

        private val throwExpApi = "https://www.bilibili.com/plus/account/exp.php"

        fun get(url: String, l_accountName: String): String {
            val mClassBilibiliAccountList =
                LitePal.where("uname = ?", l_accountName).find<ClassBilibiliAccount>()
            val mClassBilibiliAccount = mClassBilibiliAccountList.get(0)

            var returnBody = "none"
            try {
                val okHttpClient = OkHttpClient()
                val mRequest: Request = Request.Builder()
                    .url(url)
                    .addHeader("Referer", "https://www.bilibili.com/")
                    .addHeader("Connection", "keep-alive")
                    .addHeader(
                        "User-Agent",
                        mClassBilibiliAccount.userAgent
                    )
                    .addHeader("Cookie", mClassBilibiliAccount.cookie)
                    .get()
                    .build()
                val mCall: Call = okHttpClient.newCall(mRequest)
                val mResponse: Response = mCall.execute()
                val mResponseBody: String? = mResponse.body?.string()
                if (mResponseBody != null) {
                    returnBody = mResponseBody
                }
            } catch (e: IOException) {
                LogUtil.logd("BilibiliUtil", "$url get失败")
                e.printStackTrace()
            }
            return returnBody
        }// get方法

        fun getByOri(url: String, l_userAgent: String, l_cookie: String): String {
            var returnBody = "none"
            try {
                val okHttpClient = OkHttpClient()
                val mRequest: Request = Request.Builder()
                    .url(url)
                    .addHeader("Referer", "https://www.bilibili.com/")
                    .addHeader("Connection", "keep-alive")
                    .addHeader(
                        "User-Agent",
                        l_userAgent
                    )
                    .addHeader("Cookie", l_cookie)
                    .get()
                    .build()
                val mCall: Call = okHttpClient.newCall(mRequest)
                val mResponse: Response = mCall.execute()
                val mResponseBody: String? = mResponse.body?.string()
                if (mResponseBody != null) {
                    returnBody = mResponseBody
                }
            } catch (e: IOException) {
                LogUtil.logd("BilibiliUtil", "$url get失败")
                e.printStackTrace()
            }
            return returnBody
        }// get方法

        fun post(url: String, mRequestBody: RequestBody, l_accountName: String): String {
            val mClassBilibiliAccountList =
                LitePal.where("uname = ?", l_accountName).find<ClassBilibiliAccount>()
            val mClassBilibiliAccount = mClassBilibiliAccountList.get(0)
            var returnBody = "none"
            try {
                val okHttpClient = OkHttpClient()
                val mRequest: Request = Request.Builder()
                    .url(url)
                    .addHeader("Referer", "https://www.bilibili.com/")
                    .addHeader("Connection", "keep-alive")
                    .addHeader(
                        "User-Agent",
                        mClassBilibiliAccount.userAgent
                    )
                    .addHeader("Cookie", mClassBilibiliAccount.cookie)
                    .post(mRequestBody)
                    .build()
                val mCall: Call = okHttpClient.newCall(mRequest)
                val mResponse: Response = mCall.execute()
                val mResponseBody: String? = mResponse.body?.string()
                if (mResponseBody != null) {
                    returnBody = mResponseBody
                }
            } catch (e: IOException) {
                LogUtil.logd("BilibiliUtil", "$url post失败")
                e.printStackTrace()
            }
            return returnBody
        }// post方法

        fun getReward(l_accountName: String): JSONObject {
            val mReturn = get(rewardApi, l_accountName)
//            LogUtil.logd("getReward", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_reward = JSONObject(mReturn)
                    val code = jsonObject_reward.getString("code")
                    val jsonObject_data = jsonObject_reward.getJSONObject("data")
                    val login = jsonObject_data.getString("login")
                    val watch = jsonObject_data.getString("watch")
                    val share = jsonObject_data.getString("share")
                    LogUtil.logd("状态码", code)
                    LogUtil.logd("是否登录", login)
                    LogUtil.logd("是否观看视频", watch)
                    LogUtil.logd("是否分享视频", share)
                    returnJson.put("code", code)
                    returnJson.put("login", login)
                    returnJson.put("watch", watch)
                    returnJson.put("share", share)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "getReward解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 获取用户每日任务情况

        fun getThrowExp(l_accountName: String): JSONObject {
            val mReturn = get(throwExpApi, l_accountName)
//            LogUtil.logd("getThrowExpApi", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_reward = JSONObject(mReturn)
                    val code = jsonObject_reward.getString("code")
                    val message = jsonObject_reward.getString("message")
                    val number = jsonObject_reward.getString("number")
                    LogUtil.logd("状态码", code)
                    LogUtil.logd("信息", message)
                    LogUtil.logd("投币所获得的经验", number)
                    returnJson.put("code",code)
                    returnJson.put("message",message)
                    returnJson.put("number",number)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "getThrowExpApi解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 查询每日投币获得经验数

        fun checkLogin(l_accountName: String): JSONObject {
            val mReturn = get(loginApi, l_accountName)
//            LogUtil.logd("checkLogin", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_login = JSONObject(mReturn)
                    val code = jsonObject_login.getString("code")
                    val jsonObject_data = jsonObject_login.getJSONObject("data")
                    val jsonObject_level_info = jsonObject_data.getJSONObject("level_info")
                    val current_level = jsonObject_level_info.getString("current_level")
                    val current_exp = jsonObject_level_info.getString("current_exp")
                    val next_exp = jsonObject_level_info.getString("next_exp")
                    val money = jsonObject_data.getString("money")
                    val jsonObject_wallet = jsonObject_data.getJSONObject("wallet")
                    val bcoin_balance = jsonObject_wallet.getString("bcoin_balance")
                    val vipStatus = jsonObject_data.getString("vipStatus")
                    val uname = jsonObject_data.getString("uname")
                    val face = jsonObject_data.getString("face").replace("http://", "https://")
//                    LogUtil.logd("状态码", code)
//                    LogUtil.logd("现在的等级", current_level)
//                    LogUtil.logd("现在的经验", current_exp)
//                    LogUtil.logd("下一等级经验", next_exp)
//                    LogUtil.logd("硬币数量", money)
//                    LogUtil.logd("B币数量", bcoin_balance)
//                    LogUtil.logd("VIP状态", vipStatus)
//                    LogUtil.logd("用户名称", uname)
//                    LogUtil.logd("头像地址", face)
                    returnJson.put("code", code)
                    returnJson.put("current_level", current_level)
                    returnJson.put("current_exp", current_exp)
                    returnJson.put("next_exp", next_exp)
                    returnJson.put("money", money)
                    returnJson.put("bcoin_balance", bcoin_balance)
                    returnJson.put("vipStatus", vipStatus)
                    returnJson.put("uname", uname)
                    returnJson.put("face", face)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "checkLogin解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 检查登录

        fun checkLoginByOri(l_userAgent: String, l_cookie: String): JSONObject {
            val mReturn = getByOri(loginApi, l_userAgent, l_cookie)
//            LogUtil.logd("checkLogin", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_login = JSONObject(mReturn)
                    val code = jsonObject_login.getString("code")
                    val jsonObject_data = jsonObject_login.getJSONObject("data")
                    val jsonObject_level_info = jsonObject_data.getJSONObject("level_info")
                    val current_level = jsonObject_level_info.getString("current_level")
                    val current_exp = jsonObject_level_info.getString("current_exp")
                    val next_exp = jsonObject_level_info.getString("next_exp")
                    val money = jsonObject_data.getString("money")
                    val jsonObject_wallet = jsonObject_data.getJSONObject("wallet")
                    val bcoin_balance = jsonObject_wallet.getString("bcoin_balance")
                    val vipStatus = jsonObject_data.getString("vipStatus")
                    val uname = jsonObject_data.getString("uname")
                    val face = jsonObject_data.getString("face").replace("http://", "https://")
//                    LogUtil.logd("状态码", code)
//                    LogUtil.logd("现在的等级", current_level)
//                    LogUtil.logd("现在的经验", current_exp)
//                    LogUtil.logd("下一等级经验", next_exp)
//                    LogUtil.logd("硬币数量", money)
//                    LogUtil.logd("B币数量", bcoin_balance)
//                    LogUtil.logd("VIP状态", vipStatus)
//                    LogUtil.logd("用户名称", uname)
//                    LogUtil.logd("头像地址", face)
                    returnJson.put("code", code)
                    returnJson.put("current_level", current_level)
                    returnJson.put("current_exp", current_exp)
                    returnJson.put("next_exp", next_exp)
                    returnJson.put("money", money)
                    returnJson.put("bcoin_balance", bcoin_balance)
                    returnJson.put("vipStatus", vipStatus)
                    returnJson.put("uname", uname)
                    returnJson.put("face", face)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "checkLogin解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 检查登录

        fun getVideo(l_accountName: String,l_rid: String): JSONObject {
            val mReturn = get(videoGetApi+l_rid, l_accountName)
//            LogUtil.logd("checkLogin", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_login = JSONObject(mReturn)
                    val jsonObject_data = jsonObject_login.getJSONObject("data")
                    val jsonArray_archives = jsonObject_data.getJSONArray("archives")
                    val jsonObject_oneVideo = jsonArray_archives.getJSONObject(0)
                    val title = jsonObject_oneVideo.getString("title")
                    val aid = jsonObject_oneVideo.getString("aid")
                    val cid = jsonObject_oneVideo.getString("cid")
                    val bvid = jsonObject_oneVideo.getString("bvid")
                    val duration = jsonObject_oneVideo.getString("duration")
                    LogUtil.logd("视频标题", title)
                    LogUtil.logd("视频av号", aid)
                    LogUtil.logd("视频cid", cid)
                    LogUtil.logd("视频bv号", bvid)
                    LogUtil.logd("视频时长", duration)
                    returnJson.put("title", title)
                    returnJson.put("aid", aid)
                    returnJson.put("cid", cid)
                    returnJson.put("bvid", bvid)
                    returnJson.put("duration", duration)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "getVideo解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 获取一个视频

        fun watchViedo(l_accountName: String, l_bvid: String, l_played_time: String): String {
            val requestBody: RequestBody = FormBody.Builder()
                .add("bvid", l_bvid)
                .add("played_time", l_played_time)
                .build()
            val mReturn = post(videoHeartbeatApi, requestBody, l_accountName)
            LogUtil.logd("watchViedo", mReturn)

            var returnString = "-1"
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_watch = JSONObject(mReturn)
                    val code = jsonObject_watch.getString("code")
                    LogUtil.logd("观看返回", code)
                    returnString = code

                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "watchViedo解析失败")
                    e.printStackTrace()
                }
            }
            return returnString

        }// 观看视频心跳包上传

        fun shareViedo(l_accountName: String, l_bvid: String, l_csrf: String): String {
            val requestBody: RequestBody = FormBody.Builder()
                .add("bvid", l_bvid)
                .add("csrf", l_csrf)
                .build()
            val mReturn = post(videoShareApi, requestBody, l_accountName)
            LogUtil.logd("shareViedo", mReturn)
            var returnString = "-1"
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_share = JSONObject(mReturn)
                    val code = jsonObject_share.getString("code")
                    LogUtil.logd("分享返回", code)
                    returnString = code

                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "shareViedo解析失败")
                    e.printStackTrace()
                }
            }
            return returnString

        }// 分享视频

        fun ThrowCoin(
            l_accountName: String,
            l_bvid: String,
            l_multiply: String,
            l_select_like: String,
            l_csrf: String
        ): String {
            val requestBody: RequestBody = FormBody.Builder()
                .add("bvid", l_bvid)
                .add("multiply", l_multiply)
                .add("select_like", l_select_like)
                .add("csrf", l_csrf)
                .build()

            val mReturn = post(throwCoinApi, requestBody, l_accountName)
            LogUtil.logd("ThrowCoin", mReturn)
            var returnString = "-1"
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_throwCoin = JSONObject(mReturn)
                    val code = jsonObject_throwCoin.getString("code")
                    LogUtil.logd("投币返回", code)
                    returnString = code
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "ThrowCoin解析失败")
                    e.printStackTrace()
                }
            }
            return returnString

        }// 投币

        fun mangaSign(l_accountName: String, l_platform: String): String {
            val requestBody: RequestBody = FormBody.Builder()
                .add("platform", l_platform)
                .build()

            val mReturn = post(mangaApi, requestBody, l_accountName)
            LogUtil.logd("mangaSign", mReturn)
            var returnString = "-1"
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_share = JSONObject(mReturn)
                    val code = jsonObject_share.getString("code")
                    LogUtil.logd("分享返回", code)
                    returnString = code

                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "mangaSign解析失败")
                    e.printStackTrace()
                }
            }
            return returnString

        }// 漫画签到 返回invalid_argument,暂不可用

        fun silver2Coin(l_accountName: String): JSONObject {
            val mReturn = get(silver2CoinApi, l_accountName)
            LogUtil.logd("silver2Coin", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_silver2Coin = JSONObject(mReturn)
                    val code = jsonObject_silver2Coin.getString("code")
                    val msg = jsonObject_silver2Coin.getString("msg")
                    LogUtil.logd("状态码", code)
                    LogUtil.logd("回调信息", msg)
                    returnJson.put("code", code)
                    returnJson.put("msg", msg)
                } catch (e: JSONException) {
                    LogUtil.logd("silver2Coin", "silver2Coin解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 银瓜子换硬币

        fun silver2CoinStatus(l_accountName: String): JSONObject {
            val mReturn = get(silver2CoinStatusApi, l_accountName)
            LogUtil.logd("silver2CoinStatus", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_silver2CoinStatus = JSONObject(mReturn)
                    val code = jsonObject_silver2CoinStatus.getString("code")
                    val jsonObject_data = jsonObject_silver2CoinStatus.getJSONObject("data")
                    val silver = jsonObject_data.getString("silver")
                    LogUtil.logd("状态码", code)
                    LogUtil.logd("银瓜子数量", silver)
                    returnJson.put("code", code)
                    returnJson.put("silver", silver)
                } catch (e: JSONException) {
                    LogUtil.logd("BilibiliUtil", "silver2CoinStatus解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 银瓜子换硬币状态查询

        fun liveSign(l_accountName: String): JSONObject {
            val mReturn = get(liveSignApi, l_accountName)
            LogUtil.logd("liveSign", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_liveSign = JSONObject(mReturn)
                    val code = jsonObject_liveSign.getString("code")
                    val message = jsonObject_liveSign.getString("message")
                    val jsonObject_data = jsonObject_liveSign.getJSONObject("data")
                    val text = jsonObject_data.getString("text")
                    val specialText = jsonObject_data.getString("specialText")
                    LogUtil.logd("状态码", code)
                    LogUtil.logd("返回信息", message)
                    LogUtil.logd("data", "本次签到获得$text,$specialText")
                    returnJson.put("code", code)
                    returnJson.put("message", message)
                    returnJson.put("text", text)
                    returnJson.put("specialText", specialText)
                } catch (e: JSONException) {
                    LogUtil.logd("liveSign", "liveSign解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 直播签到


    }
}