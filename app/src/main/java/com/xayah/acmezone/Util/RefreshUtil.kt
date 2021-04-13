package com.xayah.acmezone.Util

import com.xayah.acmezone.Class.ClassBilibiliAccount
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.Class.ClassTieBaForum
import org.litepal.LitePal
import org.litepal.extension.find
import java.text.SimpleDateFormat
import java.util.*

class RefreshUtil {
    companion object {
        fun GetTime(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val signTime =
                simpleDateFormat.format(Date(System.currentTimeMillis().toString().toLong()))
            LogUtil.logd("getTime", signTime)
            return signTime
        }

        fun RefreshBilibiliTasks(mClassBilibiliAccount: ClassBilibiliAccount) {
            mClassBilibiliAccount.stateLiveSign = "unknown"
            mClassBilibiliAccount.stateSilver2Coin = "unknown"
            mClassBilibiliAccount.stateThrow = "unknown"
            mClassBilibiliAccount.stateShare = "unknown"
            mClassBilibiliAccount.stateWatch = "unknown"
            mClassBilibiliAccount.stateLogin = "unknown"
            mClassBilibiliAccount.saveOrUpdate()
        }

        fun SetBilibiliSignedTime(mClassBilibiliAccount: ClassBilibiliAccount) {
            mClassBilibiliAccount.signedTime = GetTime()
            mClassBilibiliAccount.saveOrUpdate()
        }

        fun RefreshTieBaAccount(mClassTieBaAccount: ClassTieBaAccount) {
            mClassTieBaAccount.state = "unknown"
            mClassTieBaAccount.signed_forum_num = "?"
            mClassTieBaAccount.saveOrUpdate("name = ?", mClassTieBaAccount.name)
            val mClassTieBaForumList =
                LitePal.where("account_name = ?", mClassTieBaAccount.name)
                    .find<ClassTieBaForum>()
            for (mClassTieBaForum in mClassTieBaForumList) {
                mClassTieBaForum.state = "unknown"
                mClassTieBaForum.saveOrUpdate(
                    "name = ? and account_name = ?",
                    mClassTieBaForum.name,
                    mClassTieBaAccount.name
                )
            }
        }

        fun SetTieBaSignedTime(mClassTieBaAccount: ClassTieBaAccount) {
            mClassTieBaAccount.signedTime = GetTime()
            mClassTieBaAccount.saveOrUpdate("name = ?", mClassTieBaAccount.name)
        }


        fun GetRandomTime(): Long {
            val randoms = (1000..3000).random()
            LogUtil.logd("randoms", randoms.toString())
            return randoms.toLong()
        }
    }
}