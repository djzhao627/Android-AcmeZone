package com.xayah.acmezone.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xayah.acmezone.R

class ActivityBilibiliLoginByParameter : AppCompatActivity() {
    private lateinit var bilibili_login_parameter_floatingActionButton: ExtendedFloatingActionButton

    private lateinit var bilibili_login_parameter_textInputEditText_userAgent: TextInputEditText
    private lateinit var bilibili_login_parameter_textInputEditText_DedeUserID: TextInputEditText
    private lateinit var bilibili_login_parameter_textInputEditText_bili_jct: TextInputEditText
    private lateinit var bilibili_login_parameter_textInputEditText_SESSDATA: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_login_by_parameter)

        bilibili_login_parameter_floatingActionButton =
            findViewById(R.id.bilibili_login_parameter_floatingActionButton)

        bilibili_login_parameter_textInputEditText_userAgent =
            findViewById(R.id.bilibili_login_parameter_textInputEditText_userAgent)
        bilibili_login_parameter_textInputEditText_DedeUserID =
            findViewById(R.id.bilibili_login_parameter_textInputEditText_DedeUserID)
        bilibili_login_parameter_textInputEditText_bili_jct =
            findViewById(R.id.bilibili_login_parameter_textInputEditText_bili_jct)
        bilibili_login_parameter_textInputEditText_SESSDATA =
            findViewById(R.id.bilibili_login_parameter_textInputEditText_SESSDATA)



        bilibili_login_parameter_floatingActionButton.setOnClickListener {
//            finish()
            runOnUiThread {
                Toast.makeText(this, "暂不支持参数登录!", Toast.LENGTH_SHORT).show()
            }
//            Thread {
//                LitePalUtil.SaveBilibiliAccount(
//                    bilibili_login_parameter_textInputEditText_userAgent.text.toString(),
//                    bilibili_login_parameter_textInputEditText_DedeUserID.text.toString(),
//                    bilibili_login_parameter_textInputEditText_bili_jct.text.toString(),
//                    bilibili_login_parameter_textInputEditText_SESSDATA.text.toString()
//                )

//            }

        }
    }
}