package com.xayah.acmezone.Util

import android.util.Log

class LogUtil {
    companion object {
        fun logd(title: String, content: String) {
            Log.d("AcmeZone", "$title: $content");
        }
    }
}