package com.xayah.acmezone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LitePalUtil

class ActivityTieBaLoginByParameter : AppCompatActivity() {
    lateinit var tieba_login_parameter_textInputEditText_userAgent: TextInputEditText
    lateinit var tieba_login_parameter_textInputEditText_BDUSS: TextInputEditText
    lateinit var tieba_login_parameter_floatingActionButton: ExtendedFloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tieba_login_by_parameter)

        tieba_login_parameter_textInputEditText_userAgent =
            findViewById(R.id.tieba_login_parameter_textInputEditText_userAgent)
        tieba_login_parameter_textInputEditText_BDUSS =
            findViewById(R.id.tieba_login_parameter_textInputEditText_BDUSS)
        tieba_login_parameter_floatingActionButton =
            findViewById(R.id.tieba_login_parameter_floatingActionButton)

        tieba_login_parameter_floatingActionButton.setOnClickListener {
            SaveAccount()
        }
    }

    private fun SaveAccount() {
        Thread {
            LitePalUtil.SaveTieBaAccount(
                tieba_login_parameter_textInputEditText_userAgent.text.toString(),
                tieba_login_parameter_textInputEditText_BDUSS.text.toString(),
            )
            val intent = Intent(this, ActivityTieBaLogin::class.java)
            startActivity(intent)
            finish()
        }.start()

    }
}