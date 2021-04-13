package com.xayah.acmezone.Util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class UpdateUtil {
    companion object {
        fun downLoadApk(mContext: Context) {
            val okHttpClient = OkHttpClient()
            val mRequest: Request = Request.Builder()
                .url("http://www.acmezone.tk/downloads/acmezone1.0.7.2.apk")
                .build()
            val mCall: Call = okHttpClient.newCall(mRequest)
            mCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val mResponse = response
                        val inputStream: InputStream = mResponse.body!!.byteStream()
                        val total: Long = mResponse.body!!.contentLength()
                        val mApk = File(mContext.filesDir.path + "/acmezone.apk")
                        val fileOutputStream: FileOutputStream = FileOutputStream(mApk)
                        val byte = ByteArray(2048)
                        var len: Int = inputStream.read(byte)
                        var sum = 0
                        while ((len) != -1) {
                            fileOutputStream.write(byte, 0, len)
                            sum += len
                            var progress: Int = (sum * 1.0f / total * 100).toInt()
                            LogUtil.logd("下载进度:", progress.toString())
                            len = inputStream.read(byte)
                        }
                        fileOutputStream.flush()
                        inputStream.close()
                        fileOutputStream.close()
                        

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }

        fun getWriteRight() {

        }
    }
}