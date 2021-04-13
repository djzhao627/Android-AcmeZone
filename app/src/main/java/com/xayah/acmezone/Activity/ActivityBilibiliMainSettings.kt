package com.xayah.acmezone.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LitePalUtil
import com.xayah.acmezone.Util.LogUtil

class ActivityBilibiliMainSettings : AppCompatActivity() {
    lateinit var bilibili_main_settings_chip_login: Chip
    lateinit var bilibili_main_settings_chip_watch: Chip
    lateinit var bilibili_main_settings_chip_share: Chip
    lateinit var bilibili_main_settings_chip_throw: Chip
    lateinit var bilibili_main_settings_chip_silver2Coin: Chip
    lateinit var bilibili_main_settings_chip_liveSign: Chip

    lateinit var bilibili_main_settings_chip_watchRandom: Chip
    lateinit var bilibili_main_settings_chip_watchPartition: Chip
    lateinit var bilibili_main_settings_textInputEditText_watchRandom: TextInputEditText
    lateinit var bilibili_main_settings_textInputEditText_watchPartition: TextInputEditText


    lateinit var bilibili_main_settings_textInputEditText_throwPartition: TextInputEditText
    lateinit var bilibili_main_settings_textInputEditText_throwNum: TextInputEditText

    lateinit var bilibili_main_settings_floatingActionButton: ExtendedFloatingActionButton
    lateinit var bilibili_main_settings_nestedScrollView: NestedScrollView

    val mClassBilibiliAccount = LitePalUtil.getBilibiliAccountByIndex(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_main_settings)

        val bilibili_main_settings_imageView_back: ImageView =
            findViewById(R.id.bilibili_main_settings_imageView_back)
        bilibili_main_settings_imageView_back.setOnClickListener { finish() }

        val bilibili_main_settings_imageView_help: ImageView =
            findViewById(R.id.bilibili_main_settings_imageView_help)
        bilibili_main_settings_imageView_help.setOnClickListener {
            val intent = Intent(this, ActivityBilibiliSettingsHelp::class.java)
            startActivity(intent)
        }

        bilibili_main_settings_floatingActionButton =
            findViewById(R.id.bilibili_main_settings_floatingActionButton)


        bilibili_main_settings_chip_watchRandom =
            findViewById(R.id.bilibili_main_settings_chip_watchRandom)
        bilibili_main_settings_textInputEditText_watchRandom =
            findViewById(R.id.bilibili_main_settings_textInputEditText_watchRandom)


        bilibili_main_settings_chip_watchPartition =
            findViewById(R.id.bilibili_main_settings_chip_watchPartition)
        bilibili_main_settings_textInputEditText_watchPartition =
            findViewById(R.id.bilibili_main_settings_textInputEditText_watchPartition)

        bilibili_main_settings_textInputEditText_throwPartition =
            findViewById(R.id.bilibili_main_settings_textInputEditText_throwPartition)
        bilibili_main_settings_textInputEditText_throwNum =
            findViewById(R.id.bilibili_main_settings_textInputEditText_throwNum)

        bilibili_main_settings_textInputEditText_watchRandom.setText(mClassBilibiliAccount.specifyVideoBVID)
        bilibili_main_settings_textInputEditText_watchPartition.setText(mClassBilibiliAccount.specifyVideoPartition)
        bilibili_main_settings_textInputEditText_throwPartition.setText(mClassBilibiliAccount.throwPartition)

        bilibili_main_settings_textInputEditText_throwNum.setText(mClassBilibiliAccount.throwNum)

        bilibili_main_settings_chip_watchRandom.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bilibili_main_settings_chip_watchRandom.setText("随机视频")
                bilibili_main_settings_textInputEditText_watchRandom.isEnabled = false

                bilibili_main_settings_chip_watchPartition.isChecked = true
                bilibili_main_settings_textInputEditText_watchPartition.isEnabled = true

                mClassBilibiliAccount.isSpecifyVideo = "NO"
                mClassBilibiliAccount.specifyVideoPartition =
                    bilibili_main_settings_textInputEditText_watchPartition.text.toString()

                LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)


            } else {
                bilibili_main_settings_chip_watchRandom.setText("指定视频")
                bilibili_main_settings_textInputEditText_watchRandom.isEnabled = true

                bilibili_main_settings_chip_watchPartition.isChecked = false
                bilibili_main_settings_textInputEditText_watchPartition.isEnabled = false

                mClassBilibiliAccount.isSpecifyVideo = "YES"
                mClassBilibiliAccount.specifyVideoBVID =
                    bilibili_main_settings_textInputEditText_watchRandom.text.toString()
                LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)

            }
        }


        bilibili_main_settings_nestedScrollView =
            findViewById(R.id.bilibili_main_settings_nestedScrollView)
        bilibili_main_settings_nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            LogUtil.logd("scrollY", scrollY.toString())
            LogUtil.logd("v", v.height.toString())
            val contentView = bilibili_main_settings_nestedScrollView.getChildAt(0)
            LogUtil.logd("measuredHeight", contentView.measuredHeight.toString())
            if (scrollY + v.height == contentView.measuredHeight) {
                bilibili_main_settings_floatingActionButton.visibility = View.GONE
            } else {
                bilibili_main_settings_floatingActionButton.visibility = View.VISIBLE
            }

            bilibili_main_settings_floatingActionButton.shrink()
        }

        bilibili_main_settings_chip_login = findViewById(R.id.bilibili_main_settings_chip_login)
        bilibili_main_settings_chip_watch = findViewById(R.id.bilibili_main_settings_chip_watch)
        bilibili_main_settings_chip_share = findViewById(R.id.bilibili_main_settings_chip_share)
        bilibili_main_settings_chip_throw = findViewById(R.id.bilibili_main_settings_chip_throw)
        bilibili_main_settings_chip_silver2Coin =
            findViewById(R.id.bilibili_main_settings_chip_silver2Coin)
        bilibili_main_settings_chip_liveSign =
            findViewById(R.id.bilibili_main_settings_chip_liveSign)

        if (mClassBilibiliAccount.isSpecifyVideo.equals("NO")) {
            bilibili_main_settings_chip_watchRandom.setText("随机视频")
            bilibili_main_settings_textInputEditText_watchRandom.isEnabled = false
            bilibili_main_settings_chip_watchRandom.isChecked = true

            bilibili_main_settings_chip_watchPartition.isChecked = true
            bilibili_main_settings_textInputEditText_watchPartition.isEnabled = true
        } else {
            bilibili_main_settings_chip_watchRandom.setText("指定视频")
            bilibili_main_settings_textInputEditText_watchRandom.isEnabled = true


            bilibili_main_settings_chip_watchPartition.isChecked = false
            bilibili_main_settings_textInputEditText_watchPartition.isEnabled = false
        }


        if (mClassBilibiliAccount.tasksWatch.equals("NO")) {
            bilibili_main_settings_chip_watch.isChecked = false
        }
        if (mClassBilibiliAccount.tasksShare.equals("NO")) {
            bilibili_main_settings_chip_share.isChecked = false

        }
        if (mClassBilibiliAccount.tasksThrow.equals("NO")) {
            bilibili_main_settings_chip_throw.isChecked = false

        }
        if (mClassBilibiliAccount.tasksSilver2Coin.equals("NO")) {
            bilibili_main_settings_chip_silver2Coin.isChecked = false

        }
        if (mClassBilibiliAccount.tasksLiveSign.equals("NO")) {
            bilibili_main_settings_chip_liveSign.isChecked = false

        }

        bilibili_main_settings_chip_watch.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtil.logd("watch", isChecked.toString())
            if (isChecked) {
                mClassBilibiliAccount.tasksWatch = "YES"
            } else {
                mClassBilibiliAccount.tasksWatch = "NO"
            }
            LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
        }
        bilibili_main_settings_chip_share.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtil.logd("share", isChecked.toString())
            if (isChecked) {
                mClassBilibiliAccount.tasksShare = "YES"
            } else {
                mClassBilibiliAccount.tasksShare = "NO"
            }
            LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
        }
        bilibili_main_settings_chip_throw.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtil.logd("throw", isChecked.toString())
            if (isChecked) {
                mClassBilibiliAccount.tasksThrow = "YES"
            } else {
                mClassBilibiliAccount.tasksThrow = "NO"
            }
            LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
        }
        bilibili_main_settings_chip_silver2Coin.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtil.logd("silver2Coin", isChecked.toString())
            if (isChecked) {
                mClassBilibiliAccount.tasksSilver2Coin = "YES"
            } else {
                mClassBilibiliAccount.tasksSilver2Coin = "NO"
            }
            LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
        }
        bilibili_main_settings_chip_liveSign.setOnCheckedChangeListener { buttonView, isChecked ->
            LogUtil.logd("liveSign", isChecked.toString())
            if (isChecked) {
                mClassBilibiliAccount.tasksLiveSign = "YES"
            } else {
                mClassBilibiliAccount.tasksLiveSign = "NO"
            }
            LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
        }
        bilibili_main_settings_floatingActionButton.setOnClickListener {
            if (bilibili_main_settings_textInputEditText_throwNum.text.toString().toInt() < 0 || bilibili_main_settings_textInputEditText_throwNum.text.toString().toInt() > 5) {
                Toast.makeText(this, "请输入正确的投币数量:[0,5]", Toast.LENGTH_SHORT).show()
            } else {
                mClassBilibiliAccount.specifyVideoPartition =
                    bilibili_main_settings_textInputEditText_watchPartition.text.toString()
                mClassBilibiliAccount.specifyVideoBVID =
                    bilibili_main_settings_textInputEditText_watchRandom.text.toString()
                mClassBilibiliAccount.throwPartition =
                    bilibili_main_settings_textInputEditText_throwPartition.text.toString()
                mClassBilibiliAccount.throwNum =
                    bilibili_main_settings_textInputEditText_throwNum.text.toString()

                LitePalUtil.UpdateBilibiliAccount(mClassBilibiliAccount)
                finish()
            }
        }


    }

}