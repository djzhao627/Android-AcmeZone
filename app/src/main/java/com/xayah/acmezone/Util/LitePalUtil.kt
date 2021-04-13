package com.xayah.acmezone.Util

import com.xayah.acmezone.Class.ClassBilibiliAccount
import com.xayah.acmezone.Class.ClassTieBaAccount
import org.json.JSONException
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll

class LitePalUtil {
    companion object {
        fun SaveBilibiliAccount(
            l_userAgent: String,
            l_DedeUserID: String,
            l_bili_jct: String,
            l_SESSDATA: String,
        ) {
            try {
                val l_cookie =
                    "bili_jct=$l_bili_jct; SESSDATA=$l_SESSDATA; DedeUserID=$l_DedeUserID"
                val jsonObject_login = BilibiliUtil.checkLoginByOri(l_userAgent, l_cookie)
                val code = jsonObject_login.getString("code")
                val current_level = jsonObject_login.getString("current_level")
                val current_exp = jsonObject_login.getString("current_exp")
                val next_exp = jsonObject_login.getString("next_exp")
                val money = jsonObject_login.getString("money")
                val bcoin_balance = jsonObject_login.getString("bcoin_balance")
                val vipStatus = jsonObject_login.getString("vipStatus")
                val uname = jsonObject_login.getString("uname")
                val face = jsonObject_login.getString("face")
                LogUtil.logd("状态码", code)
                if (code.equals("0")) {
                    val mClassBilibiliAccount = ClassBilibiliAccount()
                    mClassBilibiliAccount.userAgent = l_userAgent
                    mClassBilibiliAccount.DedeUserID = l_DedeUserID
                    mClassBilibiliAccount.bili_jct = l_bili_jct
                    mClassBilibiliAccount.SESSDATA = l_SESSDATA
                    mClassBilibiliAccount.cookie = l_cookie
                    mClassBilibiliAccount.current_level = current_level
                    mClassBilibiliAccount.current_exp = current_exp
                    mClassBilibiliAccount.next_exp = next_exp
                    mClassBilibiliAccount.money = money
                    mClassBilibiliAccount.bcoin_balance = bcoin_balance
                    mClassBilibiliAccount.vipStatus = vipStatus
                    mClassBilibiliAccount.uname = uname
                    mClassBilibiliAccount.face = face
                    mClassBilibiliAccount.saveOrUpdate("uname=?", mClassBilibiliAccount.uname)

                }

            } catch (e: JSONException) {
                e.printStackTrace()

            }

        }

        fun UpdateBilibiliAccount(
            mClassBilibiliAccount: ClassBilibiliAccount,
        ) {

            mClassBilibiliAccount.saveOrUpdate("uname=?", mClassBilibiliAccount.uname)
        }

        fun getBilibiliAccountByName(l_uname: String): ClassBilibiliAccount {
            val mClassBilibiliAccountList =
                LitePal.where("uname = ?", l_uname).find<ClassBilibiliAccount>()
            val mClassBilibiliAccount = mClassBilibiliAccountList.get(0)
            return mClassBilibiliAccount
        }

        fun getBilibiliAccountByIndex(l_index: Int): ClassBilibiliAccount {
            val mClassBilibiliAccountList =
                LitePal.findAll<ClassBilibiliAccount>()
            val mClassBilibiliAccount = mClassBilibiliAccountList.get(l_index)
            return mClassBilibiliAccount
        }


        fun SaveTieBaAccount(
            l_userAgent: String,
            BDUSS: String,
        ) {
            try {
                val l_cookie =
                    "BDUSS=$BDUSS;"
                val jsonObject_login = TieBaUtil.checkLoginByOri(l_userAgent, l_cookie)
                val code = jsonObject_login.getString("code")
                val name = jsonObject_login.getString("name")
                val concern_num = jsonObject_login.getString("concern_num")
                val fans_num = jsonObject_login.getString("fans_num")
                val like_forum_num = jsonObject_login.getString("like_forum_num")
                val post_num = jsonObject_login.getString("post_num")
                val portrait_url = jsonObject_login.getString("portrait_url")
                LogUtil.logd("状态码", code)
                LogUtil.logd("贴吧名称", name)
                LogUtil.logd("贴吧关注数", concern_num)
                LogUtil.logd("贴吧粉丝数", fans_num)
                LogUtil.logd("贴吧关注贴吧数", like_forum_num)
                LogUtil.logd("贴吧发帖数", post_num)
                LogUtil.logd("账号头像地址", portrait_url)
                if (code.equals("0")) {
                    val mClassTieBaAccount = ClassTieBaAccount()
                    mClassTieBaAccount.userAgent = l_userAgent
                    mClassTieBaAccount.BDUSS = BDUSS
                    mClassTieBaAccount.cookie = l_cookie
                    mClassTieBaAccount.cookie = l_cookie
                    mClassTieBaAccount.name = name
                    mClassTieBaAccount.concern_num = concern_num
                    mClassTieBaAccount.fans_num = fans_num
                    mClassTieBaAccount.like_forum_num = like_forum_num
                    mClassTieBaAccount.post_num = post_num
                    mClassTieBaAccount.portrait_url = portrait_url
                    mClassTieBaAccount.signed_forum_num = "?"
                    mClassTieBaAccount.saveOrUpdate("name=?", mClassTieBaAccount.name)
                }

            } catch (e: JSONException) {
                e.printStackTrace()

            }

        }
    }
}