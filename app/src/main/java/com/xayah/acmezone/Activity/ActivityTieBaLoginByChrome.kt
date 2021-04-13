package com.xayah.acmezone.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import com.xayah.acmezone.Component.WebViewClientTieBa
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LitePalUtil
import com.xayah.acmezone.Util.LogUtil
import org.json.JSONException

class ActivityTieBaLoginByChrome : AppCompatActivity() {
    lateinit var tieba_login_chrome_webview: WebView
    lateinit var userAgent: String
    lateinit var BDUSS: String
    lateinit var mContext: Context
    private lateinit var tieba_login_chrome_imageView_back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tieba_login_by_chrome)
        mContext = this

        tieba_login_chrome_imageView_back =
            findViewById(R.id.tieba_login_chrome_imageView_back)
        tieba_login_chrome_imageView_back.setOnClickListener { finish() }
        tieba_login_chrome_webview = findViewById(R.id.tieba_login_chrome_webview)
        tieba_login_chrome_webview.loadUrl("https://wappass.baidu.com/")
        val settings = tieba_login_chrome_webview.settings
        // 允许与Javascript交互
        settings.javaScriptEnabled = true
        userAgent = settings.userAgentString
//        LogUtil.logd("userAgent", userAgent)
        val webViewClientTieBa = WebViewClientTieBa()
        webViewClientTieBa.setParemeterListener {
            try {
                BDUSS = it.getString("BDUSS")
                LogUtil.logd("BDUSS", BDUSS)
                runOnUiThread {
                    Toast.makeText(mContext, "登录成功!", Toast.LENGTH_SHORT).show()
                }
                // 获取成功，缓存本地
                SaveAccount()
                // 停止webview的加载
                tieba_login_chrome_webview.stopLoading()
                // 清除webview的cookies缓存
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookies { value: Boolean? -> }
            } catch (e: JSONException) {
                e.printStackTrace()
            }


        }
        tieba_login_chrome_webview.webViewClient = webViewClientTieBa
    }

    private fun SaveAccount() {
        Thread {
            LitePalUtil.SaveTieBaAccount(
                userAgent,
                BDUSS,
            )
            val intent = Intent(this, ActivityTieBaLogin::class.java)
            startActivity(intent)
            finish()
        }.start()

    }
}