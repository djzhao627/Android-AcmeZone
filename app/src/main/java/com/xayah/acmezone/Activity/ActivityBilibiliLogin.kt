package com.xayah.acmezone.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.xayah.acmezone.Class.ClassBilibiliAccount
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil
import org.litepal.LitePal
import org.litepal.extension.findAll

class ActivityBilibiliLogin : AppCompatActivity() {
    lateinit var bilibili_login_cardView_chrome: CardView
    lateinit var bilibili_login_cardView_parameter: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_login)



        bilibili_login_cardView_chrome = findViewById(R.id.bilibili_login_cardView_chrome)
        bilibili_login_cardView_chrome.setOnClickListener {
            val intent =
                Intent(this@ActivityBilibiliLogin, ActivityBilibiliLoginByChrome::class.java)
            startActivity(intent)
        }
        bilibili_login_cardView_parameter = findViewById(R.id.bilibili_login_cardView_parameter)
        bilibili_login_cardView_parameter.setOnClickListener {
            val intent =
                Intent(this@ActivityBilibiliLogin, ActivityBilibiliLoginByParameter::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        LogUtil.logd("onStart", "回到Bilibili登录界面")
        val mClassBilibiliAccountList =
            LitePal.findAll<ClassBilibiliAccount>()
        if (mClassBilibiliAccountList.size != 0) {
            finish()
            val intent = Intent(this, ActivityBilibiliMain::class.java)
            startActivity(intent)
        }
        super.onStart()
    }
}