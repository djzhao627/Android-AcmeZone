package com.xayah.acmezone.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.xayah.acmezone.Adapter.AdapterBilibiliMainTask
import com.xayah.acmezone.Class.ClassBilibiliMainTask
import com.xayah.acmezone.Component.NetImageViewCircle
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.BilibiliUtil
import com.xayah.acmezone.Util.LitePalUtil
import com.xayah.acmezone.Util.LogUtil
import com.xayah.acmezone.Util.RefreshUtil
import org.json.JSONException
import org.litepal.LitePal

class ActivityBilibiliMain : AppCompatActivity() {
    private lateinit var a_uname: String

    private lateinit var mContext: Context

    private lateinit var bilibili_main_imageView_back: ImageView
    private lateinit var bilibili_main_imageView_help: ImageView


    private lateinit var bilibili_main_recyclerView: RecyclerView
    private lateinit var mAdapterBilibiliMainTask: AdapterBilibiliMainTask
    private lateinit var bilibili_main_netImageViewCircle_head: NetImageViewCircle
    private lateinit var bilibili_main_textView_userName: TextView
    private lateinit var bilibili_main_imageView_current_level: ImageView
    private lateinit var bilibili_main_textView_money: TextView
    private lateinit var bilibili_main_textView_bcoin_balance: TextView
    private lateinit var bibili_textView_current_exp: TextView
    private lateinit var bilibili_main_textView_levelUpTime: TextView


    private lateinit var bilibili_main_floatingActionButton: ExtendedFloatingActionButton
    private lateinit var bilibili_main_nestedScrollView: NestedScrollView

    private var index: Int = 0

    private var indexWatch: Int = 0
    private var indexShare: Int = 0
    private var indexThrow: Int = 0
    private var indexSilver2Coin: Int = 0
    private var indexLiveSign: Int = 0


    private var isDoing: Boolean = false

    private var isFirstTime: Boolean = true

    var mClassBilibiliAccount = LitePalUtil.getBilibiliAccountByIndex(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_main)
        LitePal.initialize(this)
        mContext = this
        init()
        login()
    }

    private fun login() {
        mAdapterBilibiliMainTask.setItem("", "正在登录...", "doing", 0)
        Thread {
            try {
                val jsonObject_login = BilibiliUtil.checkLogin(a_uname)
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
                    LogUtil.logd("现在的等级", current_level)
                    LogUtil.logd("现在的经验", current_exp)
                    LogUtil.logd("下一等级经验", next_exp)
                    LogUtil.logd("硬币数量", money)
                    LogUtil.logd("B币数量", bcoin_balance)
                    LogUtil.logd("VIP状态", vipStatus)
                    LogUtil.logd("用户名称", uname)
                    LogUtil.logd("头像地址", face)
                    mClassBilibiliAccount.current_level = current_level
                    mClassBilibiliAccount.current_exp = current_exp
                    mClassBilibiliAccount.next_exp = next_exp
                    mClassBilibiliAccount.money = money
                    mClassBilibiliAccount.bcoin_balance = bcoin_balance
                    mClassBilibiliAccount.vipStatus = vipStatus
                    mClassBilibiliAccount.uname = uname
                    mClassBilibiliAccount.face = face
                    LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                    runOnUiThread {
                        bilibili_main_netImageViewCircle_head.setImageURL(face)
                        bilibili_main_textView_userName.text = uname
                        if (current_level == "1") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_1)
                        } else if (current_level == "2") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_2)
                        } else if (current_level == "3") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_3)
                        } else if (current_level == "4") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_4)
                        } else if (current_level == "5") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_5)
                        } else if (current_level == "6") {
                            bilibili_main_imageView_current_level.setImageResource(R.drawable.bilibili_ic_userlevel_6)
                        }
                        bilibili_main_textView_money.text = money
                        bilibili_main_textView_bcoin_balance.text = bcoin_balance
                        bibili_textView_current_exp.text = current_exp
                        if (next_exp.equals("--")) {
                            bilibili_main_textView_levelUpTime.text = "∞"
                        } else {
                            bilibili_main_textView_levelUpTime.text =
                                ((next_exp.toInt() - current_exp.toInt()) / 65).toString()
                        }


                    }
                }
                getReward()


            } catch (e: JSONException) {
                e.printStackTrace()

            }

        }.start()

    }

    private fun getReward() {
        val jsonObject_reward = BilibiliUtil.getReward(a_uname)
        val code = jsonObject_reward.getString("code")
        val login = jsonObject_reward.getString("login")
        val watch = jsonObject_reward.getString("watch")
        val share = jsonObject_reward.getString("share")
        LogUtil.logd("状态码", code)
        if (code.equals("0")) {
            LogUtil.logd("是否登录", login)
            LogUtil.logd("是否观看视频", watch)
            LogUtil.logd("是否分享视频", share)
            runOnUiThread {
                if (login.equals("true")) {
                    mClassBilibiliAccount.stateLogin = "finished"
                    LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                    mAdapterBilibiliMainTask.setItem(
                        "经验+5 (已到账)\n硬币+1 (已到账)",
                        "登录成功!",
                        "finished",
                        0
                    )
                } else {
                    mClassBilibiliAccount.stateLogin = "waiting"
                    LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                    mAdapterBilibiliMainTask.setItem(
                        "经验+5 (未到账)\n硬币+1 (未到账)",
                        "登录成功!",
                        "waiting",
                        0
                    )
                }
                if (watch.equals("true")) {
                    mClassBilibiliAccount.stateWatch = "finished"
                    LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                    mAdapterBilibiliMainTask.setItem("经验+5 (已到账)", "观看成功!", "finished", indexWatch)
                } else if (mClassBilibiliAccount.stateWatch.equals("waiting")) {
                    mAdapterBilibiliMainTask.setItem("经验+5 (未到账)", "观看成功!", "waiting", indexWatch)
                }
                if (share.equals("true")) {
                    mClassBilibiliAccount.stateShare = "finished"
                    LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                    mAdapterBilibiliMainTask.setItem("经验+5 (已到账)", "分享成功!", "finished", indexShare)
                } else if (mClassBilibiliAccount.stateShare.equals("waiting")) {
                    mAdapterBilibiliMainTask.setItem("经验+5 (未到账)", "分享成功!", "waiting", indexShare)
                }
            }

        }


    }

    private fun init() {
        if (!mClassBilibiliAccount.signedTime.equals(RefreshUtil.GetTime())) {
            RefreshUtil.RefreshBilibiliTasks(mClassBilibiliAccount)
            Toast.makeText(this, "新的一天开始啦，快快开始吧!", Toast.LENGTH_SHORT).show()
        }
        if (mClassBilibiliAccount.specifyVideoBVID == "BV1rt4y1k744") {
            mClassBilibiliAccount.isSpecifyVideo = "NO"
            mClassBilibiliAccount.specifyVideoBVID = ""
            mClassBilibiliAccount.saveOrUpdate("uname = ?", mClassBilibiliAccount.uname)
        }


        bilibili_main_imageView_back = findViewById(R.id.bilibili_main_imageView_back)
        bilibili_main_imageView_back.setOnClickListener { finish() }

        bilibili_main_imageView_help = findViewById(R.id.bilibili_main_imageView_help)
        bilibili_main_imageView_help.setOnClickListener {
            val intent = Intent(this, ActivityBilibiliMainHelp::class.java)
            startActivity(intent)
        }

        bilibili_main_recyclerView = findViewById(R.id.bilibili_main_recyclerView)
        bilibili_main_nestedScrollView = findViewById(R.id.bilibili_main_nestedScrollView)

        bilibili_main_netImageViewCircle_head =
            findViewById(R.id.bilibili_main_netImageViewCircle_head)
        bilibili_main_textView_userName = findViewById(R.id.bilibili_main_textView_userName)
        bilibili_main_imageView_current_level =
            findViewById(R.id.bilibili_main_imageView_current_level)
        bilibili_main_textView_money = findViewById(R.id.bilibili_main_textView_money)
        bilibili_main_textView_bcoin_balance =
            findViewById(R.id.bilibili_main_textView_bcoin_balance)
        bibili_textView_current_exp = findViewById(R.id.bibili_textView_current_exp)
        bilibili_main_textView_levelUpTime = findViewById(R.id.bilibili_main_textView_levelUpTime)

        bilibili_main_floatingActionButton = findViewById(R.id.bilibili_main_floatingActionButton)


        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        bilibili_main_recyclerView.setLayoutManager(layoutManager)

        InitList()


        bilibili_main_nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            LogUtil.logd("scrollY", scrollY.toString())
            LogUtil.logd("v", v.height.toString())
            val contentView = bilibili_main_nestedScrollView.getChildAt(0)
            LogUtil.logd("measuredHeight", contentView.measuredHeight.toString())

            if (scrollY + v.height == contentView.measuredHeight) {
                bilibili_main_floatingActionButton.alpha = 0.3F
            } else {
                bilibili_main_floatingActionButton.alpha = 1.0F
            }

            bilibili_main_floatingActionButton.shrink()
        }

        bilibili_main_recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        bilibili_main_recyclerView.itemAnimator = DefaultItemAnimator()

//        mAdapterBilibiliMainTask.setItem("", "正在观看...(1/99)", "doing",1)
//        mAdapterBilibiliMainTask.setItem("经验+5 (未到账)", "分享成功!", "waiting",2)
//        mAdapterBilibiliMainTask.setItem("经验+5 (已到账)\n硬币+1 (已到账)", "投币成功!", "finished",3)
        bilibili_main_floatingActionButton.setOnClickListener {
            val intent = Intent(this, ActivityBilibiliMainSettings::class.java)
            startActivity(intent)
        }
        bilibili_main_floatingActionButton.setOnLongClickListener {
            if (isDoing) {
                Toast.makeText(this, "正在完成任务~", Toast.LENGTH_SHORT).show()
            } else {
                isDoing = true
                bilibili_main_floatingActionButton.shrink()
                bilibili_main_floatingActionButton.setIconResource(R.drawable.bilibili_ic_wait)
                RefreshUtil.SetBilibiliSignedTime(mClassBilibiliAccount)
                Thread {
                    if (mClassBilibiliAccount.tasksWatch.equals("YES")) {
                        if (!mClassBilibiliAccount.stateWatch.equals("finished")) {
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "",
                                    "正在观看视频...",
                                    "doing",
                                    indexWatch
                                )
                            }
                            Thread.sleep(RefreshUtil.GetRandomTime())
                            if (mClassBilibiliAccount.isSpecifyVideo.equals("YES")) {
                                BilibiliUtil.watchViedo(
                                    mClassBilibiliAccount.uname,
                                    mClassBilibiliAccount.specifyVideoBVID,
                                    "15"
                                )
                            } else {
                                try {
                                    val l_bvid = BilibiliUtil.getVideo(
                                        mClassBilibiliAccount.uname,
                                        mClassBilibiliAccount.specifyVideoPartition
                                    )
                                    BilibiliUtil.watchViedo(
                                        mClassBilibiliAccount.uname,
                                        l_bvid.getString("bvid"),
                                        "15"
                                    )
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "经验+5 (未到账)",
                                    "观看视频成功!",
                                    "waiting",
                                    indexWatch
                                )
                            }
                            mClassBilibiliAccount.stateWatch = "waiting"
                        }
                    }
                    if (mClassBilibiliAccount.tasksShare.equals("YES")) {
                        if (!mClassBilibiliAccount.stateShare.equals("finished")) {
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "",
                                    "正在分享视频...",
                                    "doing",
                                    indexShare
                                )
                            }
                            Thread.sleep(RefreshUtil.GetRandomTime())
                            if (mClassBilibiliAccount.isSpecifyVideo.equals("YES")) {
                                BilibiliUtil.shareViedo(
                                    mClassBilibiliAccount.uname,
                                    mClassBilibiliAccount.specifyVideoBVID,
                                    mClassBilibiliAccount.bili_jct
                                )
                            } else {
                                try {
                                    val l_bvid = BilibiliUtil.getVideo(
                                        mClassBilibiliAccount.uname,
                                        mClassBilibiliAccount.specifyVideoPartition
                                    )
                                    BilibiliUtil.shareViedo(
                                        mClassBilibiliAccount.uname,
                                        l_bvid.getString("bvid"),
                                        mClassBilibiliAccount.bili_jct
                                    )
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                            }
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "经验+5 (未到账)",
                                    "分享视频成功!",
                                    "waiting",
                                    indexShare
                                )

                            }
                            mClassBilibiliAccount.stateShare = "waiting"
                        }
                    }
                    if (mClassBilibiliAccount.tasksThrow.equals("YES")) {
                        if (!mClassBilibiliAccount.stateThrow.equals("finished")) {
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "",
                                    "正在每日投币...",
                                    "doing",
                                    indexThrow
                                )
                            }
                            try {
                                val getThrowExp =
                                    BilibiliUtil.getThrowExp(mClassBilibiliAccount.uname)
                                val number = getThrowExp.getString("number")
                                LogUtil.logd("number", number)

                                val timeSurplus =
                                    (mClassBilibiliAccount.throwNum.toInt() * 10 - number.toInt()) / 10
                                if (timeSurplus <= 0) {
                                    runOnUiThread {
                                        Toast.makeText(
                                            mContext,
                                            "还需投币:0次",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    runOnUiThread {
                                        Toast.makeText(
                                            mContext,
                                            "还需投币:" + timeSurplus + "次",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    for (i in 1..timeSurplus) {
                                        Thread.sleep(RefreshUtil.GetRandomTime())
                                        LogUtil.logd("投币:", "第" + i.toString() + "次")
                                        try {
                                            val getVideoJson = BilibiliUtil.getVideo(
                                                mClassBilibiliAccount.uname,
                                                mClassBilibiliAccount.throwPartition
                                            )
                                            val l_bvid = getVideoJson.getString("bvid")
                                            BilibiliUtil.ThrowCoin(
                                                mClassBilibiliAccount.uname,
                                                l_bvid,
                                                "1",
                                                "0",
                                                mClassBilibiliAccount.bili_jct
                                            )
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    runOnUiThread {
                                        mAdapterBilibiliMainTask.setItem(
                                            "经验+50 (已到账)",
                                            "每日投币成功!",
                                            "finished",
                                            indexThrow
                                        )
                                    }
                                    mClassBilibiliAccount.stateThrow = "finished"
                                }
                                LogUtil.logd("timeSurplus", timeSurplus.toString())
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    if (mClassBilibiliAccount.tasksSilver2Coin == "YES") {
                        if (mClassBilibiliAccount.stateSilver2Coin != "finished") {
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "",
                                    "正在兑换硬币...",
                                    "doing",
                                    indexSilver2Coin
                                )
                            }
                            Thread.sleep(RefreshUtil.GetRandomTime())
                            BilibiliUtil.silver2Coin(mClassBilibiliAccount.uname)
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "硬币+1 (已到账)",
                                    "兑换硬币成功!",
                                    "finished",
                                    indexSilver2Coin
                                )

                            }
                            mClassBilibiliAccount.stateSilver2Coin = "finished"
                            BilibiliUtil.silver2CoinStatus(mClassBilibiliAccount.uname)
                        }
                    }
                    if (mClassBilibiliAccount.tasksLiveSign.equals("YES")) {
                        if (mClassBilibiliAccount.stateLiveSign != "finished") {
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    "",
                                    "正在直播签到...",
                                    "doing",
                                    indexLiveSign
                                )

                            }
                            Thread.sleep(RefreshUtil.GetRandomTime())
                            try {
                                val jsonObject_liveSign =
                                    BilibiliUtil.liveSign(mClassBilibiliAccount.uname)
                                val text = jsonObject_liveSign.getString("text")
                                val specialText = jsonObject_liveSign.getString("specialText")
                                val liveSignReward = "本次签到获得$text,$specialText"
                                mClassBilibiliAccount.liveSignReward = liveSignReward
                                mClassBilibiliAccount.saveOrUpdate()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            runOnUiThread {
                                mAdapterBilibiliMainTask.setItem(
                                    mClassBilibiliAccount.liveSignReward,
                                    "直播签到成功!",
                                    "finished",
                                    indexLiveSign
                                )
                            }
                            mClassBilibiliAccount.stateLiveSign = "finished"
                        }

                    }
                    mClassBilibiliAccount.saveOrUpdate()
                    runOnUiThread {
                        Toast.makeText(this, "任务已全部完成啦！", Toast.LENGTH_SHORT).show()
                        bilibili_main_floatingActionButton.setIconResource(R.drawable.bilibili_ic_check)
                    }


                }.start()

            }



            true
        }


    }

    private fun InitList() {
        a_uname = mClassBilibiliAccount.uname
        val mClassBilibiliMainTaskList: MutableList<ClassBilibiliMainTask?> = mutableListOf()
        mClassBilibiliMainTaskList.add(
            ClassBilibiliMainTask(
                "每日登录",
                "未登录",
                "",
                mClassBilibiliAccount.stateLogin
            )
        )

        if (mClassBilibiliAccount.tasksWatch.equals("YES")) {
            index++;
            indexWatch = index
            mClassBilibiliMainTaskList.add(
                ClassBilibiliMainTask(
                    "观看视频",
                    "未观看",
                    "",
                    "unknown"
                )
            )
        }
        if (mClassBilibiliAccount.tasksShare.equals("YES")) {
            index++;
            indexShare = index
            mClassBilibiliMainTaskList.add(
                ClassBilibiliMainTask(
                    "分享视频",
                    "未分享",
                    "",
                    "unknown"
                )
            )
        }
        if (mClassBilibiliAccount.tasksThrow.equals("YES")) {
            index++;
            indexThrow = index
            if (mClassBilibiliAccount.stateThrow.equals("finished")) {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "每日投币",
                        "每日投币成功!",
                        "经验+50 (已到账)",
                        mClassBilibiliAccount.stateThrow
                    )
                )
            } else {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "每日投币",
                        "未投币",
                        "",
                        "unknown"
                    )
                )
            }


        }
        if (mClassBilibiliAccount.tasksSilver2Coin.equals("YES")) {
            index++;
            indexSilver2Coin = index
            if (mClassBilibiliAccount.stateSilver2Coin.equals("finished")) {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "兑换硬币",
                        "已兑换",
                        "硬币+1 (已到账)",
                        mClassBilibiliAccount.stateSilver2Coin
                    )
                )
            } else {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "兑换硬币",
                        "未兑换",
                        "",
                        mClassBilibiliAccount.stateSilver2Coin
                    )
                )
            }

        }
        if (mClassBilibiliAccount.tasksLiveSign.equals("YES")) {
            index++;
            indexLiveSign = index
            if (mClassBilibiliAccount.stateLiveSign.equals("finished")) {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "直播签到",
                        "已签到",
                        mClassBilibiliAccount.liveSignReward,
                        mClassBilibiliAccount.stateLiveSign
                    )
                )
            } else {
                mClassBilibiliMainTaskList.add(
                    ClassBilibiliMainTask(
                        "直播签到",
                        "未签到",
                        "",
                        mClassBilibiliAccount.stateLiveSign
                    )
                )
            }

        }

        mAdapterBilibiliMainTask = AdapterBilibiliMainTask(this, mClassBilibiliMainTaskList)
        bilibili_main_recyclerView.adapter = mAdapterBilibiliMainTask
    }

    override fun onStart() {
        mClassBilibiliAccount = LitePalUtil.getBilibiliAccountByIndex(0)
        if (isFirstTime) {
            isFirstTime = false;
        } else {
            finish()
            startActivity(intent)
        }
        super.onStart()
    }
}