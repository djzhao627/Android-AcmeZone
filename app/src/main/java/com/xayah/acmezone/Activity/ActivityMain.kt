package com.xayah.acmezone.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.xayah.acmezone.Class.ClassBilibiliAccount
import com.xayah.acmezone.Component.NetImageView
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.litepal.LitePal
import org.litepal.extension.findAll
import java.io.IOException
import java.util.*

class ActivityMain : AppCompatActivity() {
    private lateinit var main_coordinatorLayout: CoordinatorLayout
    private lateinit var main_imageView_menu: ImageView
    private lateinit var main_cardview_qq: CardView
    private lateinit var main_cardview_wechat: CardView
    private lateinit var main_cardview_bilibili: CardView
    private lateinit var main_cardview_tieba: CardView
    private lateinit var main_cardview_qqmusic: CardView
    private lateinit var main_cardview_neteasemusic: CardView

    private lateinit var main_navigationView: NavigationView
    private lateinit var main_drawer_layout: DrawerLayout
    lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LitePal.initialize(this)
        mContext = this
        checkVersion()
        main_coordinatorLayout = findViewById(R.id.main_coordinatorLayout)
//        Thread{
//            LitePalUtil.SaveBilibiliAccount(
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
//                "22831515", "6cf2657d899ae938fc271c5889d655d3", "0504884d%2C1624960702%2Cd6c7d*c1"
//            )
//        }.start()
        Thread {
//            UpdateUtil.downLoadApk(this)
        }.start()
        main_navigationView = findViewById(R.id.main_navigationView)
        main_drawer_layout = findViewById(R.id.main_drawer_layout)
        val navigationview_head = main_navigationView.getHeaderView(0)
        val navigationview_head_textView_version: TextView =
            navigationview_head.findViewById(R.id.navigationview_head_textView_version)
        navigationview_head_textView_version.text = "AcmeZone v" + getVersion()

        main_navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_thanks_chamaoyun -> {
                    val uri = Uri.parse("https://www.chamaoyun.com/")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                R.id.item_thanks_Github -> {
                    val uri = Uri.parse("https://github.com/")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                R.id.item_update -> {
                    Thread {
                        try {
                            Looper.prepare()
                            val getTbsUrl =
                                "https://service-jvdpo3qg-1303879152.gz.apigw.tencentcs.com/release/AcmeZoneUpdateInfo"
                            val okHttpClient = OkHttpClient()
                            val mRequest: Request = Request.Builder()
                                .url(getTbsUrl)
                                .get()
                                .build()
                            val mCall: Call = okHttpClient.newCall(mRequest)
                            val mResponse: Response = mCall.execute()
                            val mResponseBody: String? = mResponse.body?.string()
                            LogUtil.logd("mResponseBody", mResponseBody!!)
                            val builder = AlertDialog.Builder(mContext)
                                .setTitle("版本前瞻")
                                .setMessage(mResponseBody)
                                .setCancelable(true)
                                .setPositiveButton("还不错") { _: DialogInterface?, which: Int -> }
                                .setNegativeButton("没啥用") { _: DialogInterface?, which: Int -> }
                                .create()
                            builder.show()
                            builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                            builder.getButton(DialogInterface.BUTTON_NEGATIVE)
                                .setTextColor(Color.BLUE);
                            Looper.loop()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }.start()


                }
                R.id.item_donate -> {
                    val mView: View = LayoutInflater.from(this)
                        .inflate(R.layout.alertdialog_imageview, null, false)
                    val imageView: NetImageView = mView.findViewById(R.id.alertdialog_imageView)
                    imageView.setImageURL("http://www.acmezone.tk/images/donate.png")
                    val builder = AlertDialog.Builder(mContext)
                        .setView(mView)
                        .setCancelable(true)
                        .create()
                    builder.show()
                }
                R.id.item_about_wechat -> {
                    val mView: View = LayoutInflater.from(this)
                        .inflate(R.layout.alertdialog_imageview, null, false)
                    val imageView: NetImageView = mView.findViewById(R.id.alertdialog_imageView)
                    imageView.setImageURL("http://www.acmezone.tk/images/VX.png")
                    val builder = AlertDialog.Builder(mContext)
                        .setView(mView)
                        .setCancelable(true)
                        .create()
                    builder.show()
                }


            }
            false
        }

        main_imageView_menu = findViewById(R.id.main_imageView_menu)
        main_imageView_menu.setOnClickListener {
//            val intent = Intent(this@ActivityMain, ActivityAboutApp::class.java)
//            startActivity(intent)
            main_drawer_layout.openDrawer(main_navigationView)
        }

        main_cardview_qq = findViewById(R.id.main_cardview_qq)
        main_cardview_qq.setOnClickListener {
            Snackbar.make(main_coordinatorLayout, "还在施工中呢！", Snackbar.LENGTH_INDEFINITE)
                .setAction("好吧") { v1: View? -> }
                .show()
        }

        main_cardview_wechat = findViewById(R.id.main_cardview_wechat)
        main_cardview_wechat.setOnClickListener {
            Snackbar.make(main_coordinatorLayout, "还在施工中呢！", Snackbar.LENGTH_INDEFINITE)
                .setAction("好吧") { v1: View? -> }
                .show()
        }

        main_cardview_bilibili = findViewById(R.id.main_cardview_bilibili)
        main_cardview_bilibili.setOnClickListener {
            val mClassBilibiliAccountList =
                LitePal.findAll<ClassBilibiliAccount>()
            if (mClassBilibiliAccountList.size == 0) {
                val intent = Intent(this@ActivityMain, ActivityBilibiliLogin::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@ActivityMain, ActivityBilibiliMain::class.java)
                startActivity(intent)
            }
//            val builder = AlertDialog.Builder(mContext)
//                .setTitle("警告")
//                .setMessage("最近有很多用户收到了哔哩哔哩官方的警告信，建议先停止使用一段时间\n确定要继续使用吗")
//                .setCancelable(true)
//                .setPositiveButton("确定") { _: DialogInterface?, which: Int ->
//                    val mClassBilibiliAccountList =
//                        LitePal.findAll<ClassBilibiliAccount>()
//                    if (mClassBilibiliAccountList.size == 0) {
//                        val intent = Intent(this@ActivityMain, ActivityBilibiliLogin::class.java)
//                        startActivity(intent)
//                    } else {
//                        val intent = Intent(this@ActivityMain, ActivityBilibiliMain::class.java)
//                        startActivity(intent)
//                    }
//                }
//                .setNegativeButton("算了") { _: DialogInterface?, which: Int -> }
//                .create()
//            builder.show()
//            builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
//            builder.getButton(DialogInterface.BUTTON_NEGATIVE)
//                .setTextColor(Color.BLUE);
        }

        main_cardview_tieba = findViewById(R.id.main_cardview_tieba)
        main_cardview_tieba.setOnClickListener {
//            val mClassBilibiliAccountList =
//                LitePal.findAll<ClassBilibiliAccount>()
//            if (mClassBilibiliAccountList.size == 0) {
//                val intent = Intent(this@ActivityMain, ActivityBilibiliLogin::class.java)
//                startActivity(intent)
//            } else {
//                val intent = Intent(this@ActivityMain, ActivityBilibiliMain::class.java)
//                startActivity(intent)
//            }
            val intent = Intent(this@ActivityMain, ActivityTieBaLogin::class.java)
            startActivity(intent)
        }

        main_cardview_qqmusic = findViewById(R.id.main_cardview_qqmusic)
        main_cardview_qqmusic.setOnClickListener {
            Snackbar.make(main_coordinatorLayout, "还在施工中呢！", Snackbar.LENGTH_INDEFINITE)
                .setAction("好吧") { v1: View? -> }
                .show()
        }

        main_cardview_neteasemusic = findViewById(R.id.main_cardview_neteasemusic)
        main_cardview_neteasemusic.setOnClickListener {
            Snackbar.make(main_coordinatorLayout, "还在施工中呢！", Snackbar.LENGTH_INDEFINITE)
                .setAction("好吧") { v1: View? -> }
                .show()
        }


    }

    fun checkVersion() {
        Thread {
            val mVersion: String? = getVersion()
            LogUtil.logd("mVersion", mVersion!!)
            val getTbsUrl =
                "https://service-l60knmjs-1303879152.gz.apigw.tencentcs.com/release/AcmeZoneCheckUpdateNodeJS"
            val okHttpClient = OkHttpClient()
            val mRequest: Request = Request.Builder()
                .url(getTbsUrl)
                .addHeader("Cookie", mVersion)
                .get()
                .build()
            val mCall: Call = okHttpClient.newCall(mRequest)
            val mResponse: Response = mCall.execute()
            val mResponseBody: String? = mResponse.body?.string()
            if (mResponseBody != null) {
                val ifTheNewest = Objects.requireNonNull(mResponseBody)
                LogUtil.logd("ifTheNewest", ifTheNewest)
                if (ifTheNewest == "1") {
                    // 已经是最新版本
                } else {
                    try {
                        val jsonObject = JSONObject(ifTheNewest)
                        LogUtil.logd("newestVersion", jsonObject.getString("newestVersion"))
                        LogUtil.logd("localVersion", jsonObject.getString("localVersion"))
                        LogUtil.logd("content", jsonObject.getString("content"))
                        LogUtil.logd("downloadUrl", jsonObject.getString("downloadUrl"))
                        LogUtil.logd("title", jsonObject.getString("title"))
                        LogUtil.logd("confirmButton", jsonObject.getString("confirmButton"))
                        LogUtil.logd("cancelButton", jsonObject.getString("cancelButton"))
                        LogUtil.logd("ifTheNewest", ifTheNewest)
                        val newestVersion = jsonObject.getString("newestVersion")
                        val localVersion = jsonObject.getString("localVersion")
                        val downloadUrl = jsonObject.getString("downloadUrl")
                        val title = jsonObject.getString("title")
                        val confirmButton = jsonObject.getString("confirmButton")
                        val cancelButton = jsonObject.getString("cancelButton")
                        val contentSplit = jsonObject.getString("content").split("&").toTypedArray()
                        val content = StringBuilder()
                        for (s in contentSplit) {
                            content.append(s).append("\n")
                        }
                        var showContent = "最新版本：$newestVersion\n"
                        showContent += "当前版本：$localVersion\n\n"
                        showContent += "更新内容：\n$content\n"
                        showContent += "是否立即更新？"
                        Looper.prepare()
                        val builder = AlertDialog.Builder(mContext)
                            .setTitle(title)
                            .setMessage(showContent)
                            .setCancelable(false)
                            .setPositiveButton(confirmButton) { dialog: DialogInterface?, which: Int ->
                                val intent = Intent()
                                intent.action = "android.intent.action.VIEW"
                                val content_url = Uri.parse(downloadUrl)
                                intent.data = content_url
                                startActivity(intent)

                            }
                            .setNegativeButton(
                                cancelButton
                            ) { dialog: DialogInterface?, which: Int -> }
                            .create()
                        builder.show()
                        builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                        builder.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setTextColor(Color.BLUE);
                        Looper.loop()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

    fun getVersion(): String? {
        return try {
            val manager = this.packageManager
            val info = manager.getPackageInfo(this.packageName, 0)
            val version = info.versionName
            LogUtil.logd("version", version)
            version
        } catch (e: Exception) {
            e.printStackTrace()
            "无法获取到版本号"
        }
    }

}