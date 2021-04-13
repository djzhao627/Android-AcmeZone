package com.xayah.acmezone.Util

import android.util.Log
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.Class.ClassTieBaForum
import okhttp3.*
import okhttp3.internal.and
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.litepal.LitePal
import org.litepal.extension.find
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class TieBaUtil {

    companion object {

        private val userInfoApi = "https://tieba.baidu.com/mo/q/newmoindex?need_user=1&referrer=f"
        private val likeForumApi = "http://c.tieba.baidu.com/c/f/forum/like"
        private val tbsApi = "http://tieba.baidu.com/dc/common/tbs"
        private val signApi = "http://c.tieba.baidu.com/c/c/forum/sign"

        fun get(url: String, l_accountName: String): String {
            val mClassTieBaAccountList =
                LitePal.where("name = ?", l_accountName).find<ClassTieBaAccount>()
            val mClassTieBaAccount = mClassTieBaAccountList.get(0)
            var returnBody = "none"
            try {
                val okHttpClient = OkHttpClient()
                val mRequest: Request = Request.Builder()
                    .url(url)
                    .addHeader("Referer", "https://tieba.baidu.com/")
                    .addHeader("Connection", "keep-alive")
                    .addHeader(
                        "User-Agent",
                        mClassTieBaAccount.userAgent
                    )
                    .addHeader("Cookie", mClassTieBaAccount.cookie)
                    .get()
                    .build()
                val mCall: Call = okHttpClient.newCall(mRequest)
                val mResponse: Response = mCall.execute()
                val mResponseBody: String? = mResponse.body?.string()
                if (mResponseBody != null) {
                    returnBody = mResponseBody
                }
            } catch (e: IOException) {
                LogUtil.logd("TieBaUtil", "$url get失败")
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
                    .addHeader("Referer", "https://tieba.baidu.com/")
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
                LogUtil.logd("TieBaUtil", "$url get失败")
                e.printStackTrace()
            }
            return returnBody
        }// get方法

        fun post(url: String, mRequestBody: RequestBody, l_accountName: String): String {
            val mClassTieBaAccountList =
                LitePal.where("name = ?", l_accountName).find<ClassTieBaAccount>()
            val mClassTieBaAccount = mClassTieBaAccountList.get(0)
            var returnBody = "none"
            try {
                val okHttpClient = OkHttpClient()
                val mRequest: Request = Request.Builder()
                    .url(url)
                    .addHeader("Referer", "https://tieba.baidu.com/")
                    .addHeader("Connection", "keep-alive")
                    .addHeader(
                        "User-Agent",
                        mClassTieBaAccount.userAgent
                    )
                    .addHeader("Cookie", mClassTieBaAccount.cookie)
                    .post(mRequestBody)
                    .build()
                val mCall: Call = okHttpClient.newCall(mRequest)
                val mResponse: Response = mCall.execute()
                val mResponseBody: String? = mResponse.body?.string()
                if (mResponseBody != null) {
                    returnBody = mResponseBody
                }
            } catch (e: IOException) {
                LogUtil.logd("TieBaUtil", "$url post失败")
                e.printStackTrace()
            }
            return returnBody
        }// post方法

        fun checkLoginByOri(l_userAgent: String, l_cookie: String): JSONObject {
            val mReturn = getByOri(userInfoApi, l_userAgent, l_cookie)
            LogUtil.logd("checkLogin", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_login = JSONObject(mReturn)
                    val code = jsonObject_login.getString("no")
//                    LogUtil.logd("状态码", code)
                    val jsonObject_data: JSONObject = jsonObject_login.getJSONObject("data")
                    val name = jsonObject_data.getString("name")
                    val concern_num = jsonObject_data.getString("concern_num")
                    val fans_num = jsonObject_data.getString("fans_num")
                    val like_forum_num = jsonObject_data.getString("like_forum_num")
                    val post_num = jsonObject_data.getString("post_num")
                    val portrait_url = jsonObject_data.getString("portrait_url")
                    returnJson.put("code", code)
                    returnJson.put("name", name)
                    returnJson.put("concern_num", concern_num)
                    returnJson.put("fans_num", fans_num)
                    returnJson.put("like_forum_num", like_forum_num)
                    returnJson.put("post_num", post_num)
                    returnJson.put("portrait_url", portrait_url)
                } catch (e: JSONException) {
                    LogUtil.logd("TieBaUtil", "checkLogin解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 检查登录

        fun getAndSaveLikeForum(l_BDUSS: String, l_accountName: String) {
            var page_no = 0
            var has_more = ""
            do {
                page_no++
                val requestBody: FormBody.Builder = FormBody.Builder()
                requestBody.add("BDUSS", l_BDUSS)
                requestBody.add("_client_id", "wappc_1607091465885_813")
                requestBody.add("_client_type", "2")
                requestBody.add("_client_version", "12.0.8.1")
                requestBody.add("_phone_imei", "000000000000000")
                requestBody.add("from", "1008621x")
                requestBody.add("model", android.os.Build.MODEL)
                requestBody.add("net_type", "1")
                requestBody.add("page_no", page_no.toString())
                requestBody.add("page_size", "200")
                requestBody.add("timestamp", System.currentTimeMillis().toString())
                requestBody.add("vcode_tag", "11")
                requestBody.add("sign", getSign(requestBody.build()))
                val mReturn = post(likeForumApi, requestBody.build(), l_accountName)
                LogUtil.logd("getLikeForum", mReturn)
                if (!mReturn.equals("none")) {
                    var jsonObject_return = JSONObject()
                    try {
                        jsonObject_return = JSONObject(mReturn)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        has_more = jsonObject_return.getString("has_more")
                    } catch (e: JSONException) {
                        LogUtil.logd("TieBaUtil", "checkLogin解析失败")
                        e.printStackTrace()
                    }
                    var jsonObject_forum_list = JSONObject()
                    try {
                        jsonObject_forum_list = jsonObject_return.getJSONObject("forum_list")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    var jsonArray_non_gconforum: JSONArray
                    try {
                        jsonArray_non_gconforum =
                            jsonObject_forum_list.getJSONArray("non-gconforum")
                        for (i in 1..jsonArray_non_gconforum.length()) {
                            val id = jsonArray_non_gconforum.getJSONObject(i - 1).getString("id")
                            val name =
                                jsonArray_non_gconforum.getJSONObject(i - 1).getString("name")
                            val cur_score =
                                jsonArray_non_gconforum.getJSONObject(i - 1).getString("cur_score")
                            val level_id =
                                jsonArray_non_gconforum.getJSONObject(i - 1).getString("level_id")
                            val levelup_score = jsonArray_non_gconforum.getJSONObject(i - 1)
                                .getString("levelup_score")
                            val mOriClassTieBaForumList =
                                LitePal.where("name = ? and account_name = ?", name, l_accountName)
                                    .find<ClassTieBaForum>()
                            if (mOriClassTieBaForumList.size == 0) {
                                val mClassTieBaForum = ClassTieBaForum()
                                mClassTieBaForum.fid = id
                                mClassTieBaForum.account_name = l_accountName
                                mClassTieBaForum.name = name
                                mClassTieBaForum.cur_score = cur_score
                                mClassTieBaForum.level_id = level_id
                                mClassTieBaForum.levelup_score = levelup_score
                                mClassTieBaForum.saveOrUpdate(
                                    "name = ? and account_name = ?",
                                    name,
                                    l_accountName
                                )
                            } else {
                                mOriClassTieBaForumList[0].fid = id
                                mOriClassTieBaForumList[0].account_name = l_accountName
                                mOriClassTieBaForumList[0].name = name
                                mOriClassTieBaForumList[0].cur_score = cur_score
                                mOriClassTieBaForumList[0].level_id = level_id
                                mOriClassTieBaForumList[0].levelup_score = levelup_score
                                mOriClassTieBaForumList[0].saveOrUpdate(
                                    "name = ? and account_name = ?",
                                    name,
                                    l_accountName
                                )
                            }


                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    var jsonArray_gconforum: JSONArray
                    try {
                        jsonArray_gconforum = jsonObject_forum_list.getJSONArray("gconforum")
                        Log.d("TAG", "jsonArray_gconforum: " + jsonArray_gconforum.length())
                        for (i in 1..jsonArray_gconforum.length()) {
                            val id = jsonArray_gconforum.getJSONObject(i - 1).getString("id")
                            val name = jsonArray_gconforum.getJSONObject(i - 1).getString("name")
                            val cur_score =
                                jsonArray_gconforum.getJSONObject(i - 1).getString("cur_score")
                            val level_id =
                                jsonArray_gconforum.getJSONObject(i - 1).getString("level_id")
                            val levelup_score =
                                jsonArray_gconforum.getJSONObject(i - 1).getString("levelup_score")
                            val mOriClassTieBaForumList =
                                LitePal.where("name = ? and account_name = ?", name, l_accountName)
                                    .find<ClassTieBaForum>()
                            if (mOriClassTieBaForumList.size == 0) {
                                val mClassTieBaForum = ClassTieBaForum()
                                mClassTieBaForum.fid = id
                                mClassTieBaForum.account_name = l_accountName
                                mClassTieBaForum.name = name
                                mClassTieBaForum.cur_score = cur_score
                                mClassTieBaForum.level_id = level_id
                                mClassTieBaForum.levelup_score = levelup_score
                                mClassTieBaForum.saveOrUpdate(
                                    "name = ? and account_name = ?",
                                    name,
                                    l_accountName
                                )
                            } else {
                                mOriClassTieBaForumList[0].fid = id
                                mOriClassTieBaForumList[0].account_name = l_accountName
                                mOriClassTieBaForumList[0].name = name
                                mOriClassTieBaForumList[0].cur_score = cur_score
                                mOriClassTieBaForumList[0].level_id = level_id
                                mOriClassTieBaForumList[0].levelup_score = levelup_score
                                mOriClassTieBaForumList[0].saveOrUpdate(
                                    "name = ? and account_name = ?",
                                    name,
                                    l_accountName
                                )
                            }
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            } while (has_more == "1")


        }// 获取关注贴吧

        fun getTbs(l_userAgent: String, l_cookie: String): JSONObject {
            val mReturn = getByOri(tbsApi, l_userAgent, l_cookie)
            LogUtil.logd("getTbs", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            if (!mReturn.equals("none")) {
                try {
                    val jsonObject_login = JSONObject(mReturn)
                    val tbs = jsonObject_login.getString("tbs")
                    returnJson.put("code", "0")
                    returnJson.put("tbs", tbs)

                } catch (e: JSONException) {
                    LogUtil.logd("TieBaUtil", "getTbs解析失败")
                    e.printStackTrace()
                }
            }
            return returnJson

        }// 签到前获取tbs


        fun sign(
            l_BDUSS: String,
            l_fid: String,
            l_kw: String,
            l_tbs: String,
            l_accountName: String
        ): JSONObject {
            val requestBody: FormBody.Builder = FormBody.Builder()
            requestBody.add("BDUSS", l_BDUSS)
            requestBody.add("_client_type", "2")
            requestBody.add("_client_version", "12.0.8.1")
            requestBody.add("_phone_imei", "000000000000000")
            requestBody.add("fid", l_fid)
            requestBody.add("kw", l_kw)
            requestBody.add("model", android.os.Build.MODEL)
            requestBody.add("net_type", "1")
            requestBody.add("tbs", l_tbs)
            requestBody.add("timestamp", System.currentTimeMillis().toString())
            requestBody.add("sign", getSign(requestBody.build()))
            val mReturn = post(signApi, requestBody.build(), l_accountName)
            LogUtil.logd("sign", mReturn)
            val returnJson = JSONObject()
            returnJson.put("code", "-1")
            try {
                val jsonObject = JSONObject(mReturn)
                val error_code = jsonObject.getString("error_code")
                returnJson.put("error_code", error_code)
                returnJson.put("code", "0")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return returnJson
        }// 贴吧签到


        fun getSign(formBody: FormBody): String {
            val sign_key = "tiebaclient!!!"
            val mString = StringBuilder()
            for (i in 0 until formBody.size) {
                mString.append(formBody.name(i)).append("=").append(formBody.value(i))
            }
            mString.append(sign_key)
            return md5Encode(mString.toString()).toUpperCase(Locale.ROOT)
        } // 通过加密算法获取sgin参数的值

        fun md5Encode(content: String): String {
            val hash: ByteArray
            hash = try {
                MessageDigest.getInstance("MD5").digest(content.toByteArray(charset("UTF-8")))
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("NoSuchAlgorithmException", e)
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("UnsupportedEncodingException", e)
            }
            //对生成的16字节数组进行补零操作
            val hex = StringBuilder(hash.size * 2)
            for (b in hash) {
                if (b and 0xFF < 0x10) {
                    hex.append("0")
                }
                hex.append(Integer.toHexString(b and 0xFF))
            }
            return hex.toString()
        } // md5加密

    }
}