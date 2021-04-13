package com.xayah.acmezone.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.xayah.acmezone.Adapter.AdapterTieBaMainAccount
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.Class.ClassTieBaForum
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil
import com.xayah.acmezone.Util.RefreshUtil
import com.xayah.acmezone.Util.TieBaUtil
import org.json.JSONException
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll
import java.io.IOException

class ActivityTieBaMain : AppCompatActivity() {
    lateinit var tieba_main_recyclerView: RecyclerView
    lateinit var mAdapterTieBaMainAccount: AdapterTieBaMainAccount
    lateinit var tieba_main_floatingActionButton: ExtendedFloatingActionButton
    var isSigning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tieba_main)
        tieba_main_recyclerView = findViewById(R.id.tieba_main_recyclerView)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        tieba_main_recyclerView.setLayoutManager(layoutManager)
        tieba_main_recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        tieba_main_recyclerView.itemAnimator = DefaultItemAnimator()
        val mClassTieBaMainAccountList =
            LitePal.findAll<ClassTieBaAccount>()
        mAdapterTieBaMainAccount = AdapterTieBaMainAccount(this, mClassTieBaMainAccountList)
        tieba_main_recyclerView.adapter = mAdapterTieBaMainAccount
        tieba_main_floatingActionButton = findViewById(R.id.tieba_main_floatingActionButton)
        tieba_main_floatingActionButton.setOnClickListener {
            val intent = Intent(this, ActivityTieBaLogin::class.java)
            intent.putExtra("isAdd", true);
            startActivity(intent)
        }
        tieba_main_floatingActionButton.setOnLongClickListener {
            if (isSigning) {
                runOnUiThread {
                    Toast.makeText(this, "正在签到中！", Toast.LENGTH_SHORT).show()
                }
            } else {
                isSigning = true
                runOnUiThread {
                    tieba_main_floatingActionButton.shrink()
                    tieba_main_floatingActionButton.setIconResource(R.drawable.bilibili_ic_wait)
                }
                (tieba_main_recyclerView.getItemAnimator() as SimpleItemAnimator).supportsChangeAnimations =
                    false
                try {
                    Thread {
                        var mClassTieBaAccountIndex = 0
                        for (mClassTieBaAccount in mClassTieBaMainAccountList) {
                            RefreshUtil.SetTieBaSignedTime(mClassTieBaAccount)
                            if (mClassTieBaAccount.state != "signed") {
                                var l_signed_forum_num = 0;
                                val mClassTieBaForumList =
                                    LitePal.where("account_name = ?", mClassTieBaAccount.name)
                                        .find<ClassTieBaForum>()
                                val tbsJson = TieBaUtil.getTbs(
                                    mClassTieBaAccount.userAgent,
                                    mClassTieBaAccount.cookie
                                )
                                val tbs = tbsJson.getString("tbs")
                                mClassTieBaAccount.state = "signing"
                                mClassTieBaAccount.saveOrUpdate("name = ?", mClassTieBaAccount.name)
                                runOnUiThread {
                                    mAdapterTieBaMainAccount.setItem(
                                        l_signed_forum_num.toString(),
                                        mClassTieBaAccountIndex
                                    )
                                }
                                for (mClassTieBaForum in mClassTieBaForumList) {
                                    if (mClassTieBaForum.state != "signed") {
                                        val signJson = TieBaUtil.sign(
                                            mClassTieBaAccount.BDUSS,
                                            mClassTieBaForum.fid,
                                            mClassTieBaForum.name,
                                            tbs,
                                            mClassTieBaAccount.name
                                        )
                                        try {
                                            val error_code = signJson.getString("error_code")
                                            when (error_code) {
                                                "160002" -> {
//                                    之前已经签到过了
                                                    l_signed_forum_num++
                                                    mClassTieBaForum.state = "signed"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
                                                    mClassTieBaAccount.signed_forum_num =
                                                        l_signed_forum_num.toString()
                                                    mClassTieBaAccount.saveOrUpdate(
                                                        "name = ?",
                                                        mClassTieBaAccount.name
                                                    )
                                                    runOnUiThread {
                                                        mAdapterTieBaMainAccount.setItem(
                                                            l_signed_forum_num.toString(),
                                                            mClassTieBaAccountIndex
                                                        )
                                                    }
                                                }
                                                "3250002" -> {
//                                    您的帐号涉及违规操作，现已被贴吧官方系统封禁，可进行申诉。
                                                    mClassTieBaForum.state = "error"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
//                                    runOnUiThread {
//                                        Toast.makeText(
//                                            this,
//                                            "您的帐号涉及违规操作，现已被贴吧官方系统封禁，可进行申诉。",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
                                                }
                                                "340006" -> {
//                                    贴吧目录出问题啦，请到贴吧签到吧反馈
                                                    mClassTieBaForum.state = "error"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
//                                    runOnUiThread {
//                                        Toast.makeText(
//                                            this,
//                                            "贴吧目录出问题啦，请到贴吧签到吧反馈",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
                                                }
                                                "340011" -> {
//                                    你签得太快了，先看看贴子再来签吧:)
                                                    mClassTieBaForum.state = "error"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
//                                    runOnUiThread {
//                                        Toast.makeText(
//                                            this,
//                                            "你签得太快了，先看看贴子再来签吧:)",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
                                                }
                                                "0" -> {
                                                    l_signed_forum_num++
                                                    mClassTieBaForum.state = "signed"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
                                                    mClassTieBaAccount.signed_forum_num =
                                                        l_signed_forum_num.toString()
                                                    mClassTieBaAccount.saveOrUpdate(
                                                        "name = ?",
                                                        mClassTieBaAccount.name
                                                    )
                                                    runOnUiThread {
                                                        mAdapterTieBaMainAccount.setItem(
                                                            l_signed_forum_num.toString(),
                                                            mClassTieBaAccountIndex
                                                        )
                                                    }
                                                }

                                                else -> {
                                                    mClassTieBaForum.state = "error"
                                                    mClassTieBaForum.saveOrUpdate(
                                                        "name = ? and account_name = ?",
                                                        mClassTieBaForum.name,
                                                        mClassTieBaAccount.name
                                                    )
                                                    runOnUiThread {
                                                        Toast.makeText(
                                                            this,
                                                            "未知错误",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    } else {
                                        l_signed_forum_num++
                                        mClassTieBaAccount.signed_forum_num =
                                            l_signed_forum_num.toString()
                                        mClassTieBaAccount.saveOrUpdate(
                                            "name = ?",
                                            mClassTieBaAccount.name
                                        )
                                        runOnUiThread {
                                            mAdapterTieBaMainAccount.setItem(
                                                l_signed_forum_num.toString(),
                                                mClassTieBaAccountIndex
                                            )
                                        }
                                    }
                                    LogUtil.logd(
                                        "l_signed_forum_num",
                                        l_signed_forum_num.toString()
                                    )


                                }
                                LogUtil.logd("签到", mClassTieBaAccount.name + "签到完成！")
                                runOnUiThread {
                                    Toast.makeText(
                                        this,
                                        mClassTieBaAccount.name + "签到完成！",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                if (mClassTieBaAccount.signed_forum_num != mClassTieBaAccount.like_forum_num) {
                                    mClassTieBaAccount.state = "refresh"
                                    mClassTieBaAccount.saveOrUpdate(
                                        "name = ?",
                                        mClassTieBaAccount.name
                                    )
                                } else {
                                    mClassTieBaAccount.state = "signed"
                                    mClassTieBaAccount.saveOrUpdate(
                                        "name = ?",
                                        mClassTieBaAccount.name
                                    )
                                }
                                runOnUiThread {
                                    mAdapterTieBaMainAccount.setItem(
                                        l_signed_forum_num.toString(),
                                        mClassTieBaAccountIndex
                                    )
                                    mClassTieBaAccountIndex++
                                }
                            } else {
                                mClassTieBaAccountIndex++
                            }

                        }
                        runOnUiThread {
                            tieba_main_floatingActionButton.setIconResource(R.drawable.bilibili_ic_check)
                        }
                    }.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                isSigning = false
            }
            true
        }
        Thread {
            for (mClassTieBaAccount in mClassTieBaMainAccountList) {
                TieBaUtil.getAndSaveLikeForum(
                    mClassTieBaAccount.BDUSS,
                    mClassTieBaAccount.name
                )
                runOnUiThread {
                    tieba_main_floatingActionButton.visibility = View.VISIBLE
                    Toast.makeText(this, mClassTieBaAccount.name + "关注贴吧更新完成！", Toast.LENGTH_SHORT)
                        .show()
                }
                if (!mClassTieBaAccount.signedTime.equals(RefreshUtil.GetTime())) {
                    RefreshUtil.RefreshTieBaAccount(mClassTieBaAccount)
                    runOnUiThread {
                        Toast.makeText(this, "新的一天开始啦，快快签到吧!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.start()
    }
}