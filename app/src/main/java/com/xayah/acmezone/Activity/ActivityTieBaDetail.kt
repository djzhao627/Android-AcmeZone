package com.xayah.acmezone.Activity

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.xayah.acmezone.Adapter.AdapterTieBaDetailForum
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.Class.ClassTieBaForum
import com.xayah.acmezone.Component.NetImageViewCircle
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil
import com.xayah.acmezone.Util.RefreshUtil
import com.xayah.acmezone.Util.TieBaUtil
import org.json.JSONException
import org.litepal.LitePal
import org.litepal.extension.find
import java.io.IOException

class ActivityTieBaDetail : AppCompatActivity() {
    lateinit var mClassTieBaAccount: ClassTieBaAccount
    lateinit var tieba_detail_recyclerView: RecyclerView
    var isSigning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tieba_detail)


        val intent: Intent = intent
        val mTieBaMainAccountStr = intent.getStringExtra("accountName")
        val mClassTieBaAccountList =
            LitePal.where("name = ?", mTieBaMainAccountStr).find<ClassTieBaAccount>()
        mClassTieBaAccount = mClassTieBaAccountList[0]

        val tieba_detail_netImageViewCircle_head: NetImageViewCircle =
            findViewById(R.id.tieba_detail_netImageViewCircle_head)
        val tieba_detail_textView_userName: TextView =
            findViewById(R.id.tieba_detail_textView_userName)
        val tieba_detail_textView_concern_num: TextView =
            findViewById(R.id.tieba_detail_textView_concern_num)
        val tieba_detail_textView_fans_num: TextView =
            findViewById(R.id.tieba_detail_textView_fans_num)
        val tieba_detail_textView_like_forum_num: TextView =
            findViewById(R.id.tieba_detail_textView_like_forum_num)
        val tieba_detail_textView_post_num: TextView =
            findViewById(R.id.tieba_detail_textView_post_num)

        tieba_detail_netImageViewCircle_head.setImageURL(mClassTieBaAccount.portrait_url)
        tieba_detail_textView_userName.text = mClassTieBaAccount.name
        tieba_detail_textView_concern_num.text = mClassTieBaAccount.concern_num
        tieba_detail_textView_fans_num.text = mClassTieBaAccount.fans_num
        tieba_detail_textView_like_forum_num.text = mClassTieBaAccount.like_forum_num
        tieba_detail_textView_post_num.text = mClassTieBaAccount.post_num

        val tieba_detail_imageView_back: ImageView = findViewById(R.id.tieba_detail_imageView_back)
        tieba_detail_imageView_back.setOnClickListener { finish() }



        tieba_detail_recyclerView = findViewById(R.id.tieba_detail_recyclerView)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tieba_detail_recyclerView.setLayoutManager(layoutManager)
        tieba_detail_recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        tieba_detail_recyclerView.itemAnimator = DefaultItemAnimator()
        val mClassTieBaDetailForumList =
            LitePal.where("account_name = ?", mClassTieBaAccount.name).find<ClassTieBaForum>()
        val mAdapterTieBaDetailForum =
            AdapterTieBaDetailForum(this, mClassTieBaDetailForumList.toMutableList())
        tieba_detail_recyclerView.adapter = mAdapterTieBaDetailForum

        val tieba_detail_floatingActionButton: ExtendedFloatingActionButton =
            findViewById(R.id.tieba_detail_floatingActionButton)
        tieba_detail_floatingActionButton.setOnLongClickListener {
            if (isSigning) {
                runOnUiThread {
                    Toast.makeText(this, "正在签到中！", Toast.LENGTH_SHORT).show()
                }
            } else {
                isSigning = true
                runOnUiThread {
                    tieba_detail_floatingActionButton.shrink()
                    tieba_detail_floatingActionButton.setIconResource(R.drawable.bilibili_ic_wait)
                }
                try {
                    Thread {
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
                            var mClassTieBaForumIndex = 0;
                            for (mClassTieBaForum in mClassTieBaForumList) {
                                tieba_detail_recyclerView.smoothScrollToPosition(
                                    mClassTieBaForumIndex
                                )
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
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
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
                                                runOnUiThread {
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
                                                }
                                            }
                                            "340006" -> {
//                                    贴吧目录出问题啦，请到贴吧签到吧反馈
                                                mClassTieBaForum.state = "error"
                                                mClassTieBaForum.saveOrUpdate(
                                                    "name = ? and account_name = ?",
                                                    mClassTieBaForum.name,
                                                    mClassTieBaAccount.name
                                                )
                                                runOnUiThread {
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
                                                }
                                            }
                                            "340011" -> {
//                                    你签得太快了，先看看贴子再来签吧:)
                                                mClassTieBaForum.state = "error"
                                                mClassTieBaForum.saveOrUpdate(
                                                    "name = ? and account_name = ?",
                                                    mClassTieBaForum.name,
                                                    mClassTieBaAccount.name
                                                )
                                                runOnUiThread {
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
                                                }
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
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
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
                                                    mAdapterTieBaDetailForum.setItem(
                                                        mClassTieBaForum.state,
                                                        mClassTieBaForumIndex
                                                    )
                                                    mClassTieBaForumIndex++
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
                                    mClassTieBaForumIndex++
                                    l_signed_forum_num++
                                    mClassTieBaAccount.signed_forum_num =
                                        l_signed_forum_num.toString()
                                    mClassTieBaAccount.saveOrUpdate(
                                        "name = ?",
                                        mClassTieBaAccount.name
                                    )
                                }
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
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this,
                                    mClassTieBaAccount.name + "签到完成！",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        runOnUiThread {
                            tieba_detail_floatingActionButton.setIconResource(R.drawable.bilibili_ic_check)
                        }
                    }.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                isSigning = false
            }
            true
        }


    }
}