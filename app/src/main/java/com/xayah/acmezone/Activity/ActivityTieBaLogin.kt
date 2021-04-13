package com.xayah.acmezone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.xayah.acmezone.Class.ClassBilibiliAccount
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil
import org.litepal.LitePal
import org.litepal.extension.findAll

class ActivityTieBaLogin : AppCompatActivity() {
    lateinit var tieba_login_cardView_chrome: CardView
    lateinit var tieba_login_cardView_parameter: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tieba_login)
        tieba_login_cardView_chrome = findViewById(R.id.tieba_login_cardView_chrome)
        tieba_login_cardView_chrome.setOnClickListener {
            val intent =
                Intent(this, ActivityTieBaLoginByChrome::class.java)
            startActivity(intent)
            finish()
        }
        tieba_login_cardView_parameter = findViewById(R.id.tieba_login_cardView_parameter)
        tieba_login_cardView_parameter.setOnClickListener {
            val intent =
                Intent(this, ActivityTieBaLoginByParameter::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        val isAdd = intent.getBooleanExtra("isAdd", false)
        if (!isAdd) {
            LogUtil.logd("onStart", "回到贴吧登录界面")
            val mClassTieBaAccountList =
                LitePal.findAll<ClassTieBaAccount>()
            if (mClassTieBaAccountList.size != 0) {
                finish()
                val intent = Intent(this, ActivityTieBaMain::class.java)
                startActivity(intent)
            }
        }

        super.onStart()
    }


}