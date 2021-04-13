package com.xayah.acmezone.Component

import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xayah.acmezone.Util.LogUtil
import org.json.JSONObject

class WebViewClientTieBa : WebViewClient() {
    private val jsonObject_return = JSONObject()
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        try {
            val cookieManager = CookieManager.getInstance()!!
            val cookieStr = cookieManager.getCookie(url)!!
            val cookieStrSplit = cookieStr.split(";")
            for (cookieItem in cookieStrSplit) {
                val cookieItemReplace = cookieItem.replace(" ", "")
                val cookieInNeed = cookieItemReplace.split("=")
                if (cookieInNeed[0].equals("BDUSS")) {
//                    LogUtil.logd("BDUSS", cookieInNeed[1])
                    jsonObject_return.put("BDUSS", cookieInNeed[1])
                    jsonObject_return.let { hasParameterListener?.invoke(it) }
                }
            }

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        super.onPageStarted(view, url, favicon)
    }


    private var hasParameterListener: ((JSONObject) -> Unit)? = null

    fun setParemeterListener(hasParameterListener: (JSONObject) -> Unit) {
        this.hasParameterListener = hasParameterListener
        hasParameterListener.invoke(jsonObject_return)
    }

}