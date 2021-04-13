package com.xayah.acmezone.Component

import android.graphics.Bitmap
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject

class WebViewClientBilibili : WebViewClient() {
    private val jsonObject_return = JSONObject()
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        try {
            val cookieManager = CookieManager.getInstance()!!
            val cookieStr = cookieManager.getCookie(url)!!
            val cookieStrSplit = cookieStr.split(";")
            for (cookieItem in cookieStrSplit) {
                val cookieItemReplace = cookieItem.replace(" ", "")
                val cookieInNeed = cookieItemReplace.split("=")
                if (cookieInNeed[0].equals("DedeUserID")) {
//                    LogUtil.logd("DedeUserID", cookieInNeed[1])
                    jsonObject_return.put("DedeUserID", cookieInNeed[1])

                }
                if (cookieInNeed[0].equals("SESSDATA")) {
//                    LogUtil.logd("SESSDATA", cookieInNeed[1])
                    jsonObject_return.put("SESSDATA", cookieInNeed[1])


                }
                if (cookieInNeed[0].equals("bili_jct")) {
//                    LogUtil.logd("bili_jct", cookieInNeed[1])
                    jsonObject_return.put("bili_jct", cookieInNeed[1])


                }
                jsonObject_return.let { hasParameterListener?.invoke(it) }
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