package com.xayah.acmezone.Activity

import android.content.Context
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xayah.acmezone.Component.WebViewClientBilibili
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LitePalUtil
import com.xayah.acmezone.Util.LogUtil
import org.json.JSONException

class ActivityBilibiliLoginByChrome : AppCompatActivity() {
    lateinit var bilibili_login_chrome_webview: WebView
    lateinit var userAgent: String
    lateinit var DedeUserID: String
    lateinit var SESSDATA: String
    lateinit var bili_jct: String
    lateinit var mContext: Context
    private lateinit var bilibili_login_chrome_imageView_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_login_by_chrome)
        mContext = this


        bilibili_login_chrome_imageView_back =
            findViewById(R.id.bilibili_login_chrome_imageView_back)
        bilibili_login_chrome_imageView_back.setOnClickListener { finish() }





        bilibili_login_chrome_webview = findViewById<WebView>(R.id.bilibili_login_chrome_webview)
        bilibili_login_chrome_webview.loadUrl("https://passport.bilibili.com/login")
        val settings = bilibili_login_chrome_webview.settings
        // 允许与Javascript交互
        settings.javaScriptEnabled = true
        userAgent = settings.userAgentString
//        LogUtil.logd("userAgent", userAgent)
        val webViewClientBilibili = WebViewClientBilibili()
        webViewClientBilibili.setParemeterListener {
            try {
                DedeUserID = it.getString("DedeUserID")
                SESSDATA = it.getString("SESSDATA")
                bili_jct = it.getString("bili_jct")
                LogUtil.logd("DedeUserID", DedeUserID)
                LogUtil.logd("SESSDATA", SESSDATA)
                LogUtil.logd("bili_jct", bili_jct)
                runOnUiThread {
                    Toast.makeText(mContext, "登录成功!", Toast.LENGTH_SHORT).show()
                }
                // 获取成功，缓存本地
                SaveAccount()
                // 停止webview的加载
                bilibili_login_chrome_webview.stopLoading()
                // 清除webview的cookies缓存
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookies { value: Boolean? -> }
            } catch (e: JSONException) {
                e.printStackTrace()
            }


        }
        bilibili_login_chrome_webview.webViewClient = webViewClientBilibili

    }

    private fun SaveAccount() {
        Thread {
            LitePalUtil.SaveBilibiliAccount(
                userAgent,
                DedeUserID,
                bili_jct,
                SESSDATA
            )
            finish()
        }.start()

    }
}