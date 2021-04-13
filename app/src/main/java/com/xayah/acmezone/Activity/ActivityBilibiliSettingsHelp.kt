package com.xayah.acmezone.Activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.style.FontStyle
import com.xayah.acmezone.Class.ClassSmartTable
import com.xayah.acmezone.R


class ActivityBilibiliSettingsHelp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_settings_help)

        val bilibili_settings_help_imageView_back: ImageView =
            findViewById(R.id.bilibili_settings_help_imageView_back)
        bilibili_settings_help_imageView_back.setOnClickListener { finish() }

        val bilibili_settings_help_smarttable =
            findViewById<SmartTable<ClassSmartTable>>(R.id.bilibili_settings_help_smarttable)
        val list: MutableList<ClassSmartTable> = ArrayList()
        list.add(ClassSmartTable("动画（主分区）", "1", ""))
        list.add(ClassSmartTable("MAD·AMV", "24", "具有一定制作程度的动画或静画的二次创作视频"))
        list.add(ClassSmartTable("MMD·3D", "25", "使用MMD（MikuMikuDance）和其他3D建模类软件制作的视频"))
        list.add(ClassSmartTable("短片·手书·配音", "47", "追求创新并具有强烈特色的短片、手书（绘）及ACG相关配音"))
        list.add(ClassSmartTable("特摄", "86", "特摄相关衍生视频"))
        list.add(ClassSmartTable("综合", "27", "以动画及动画相关内容为素材，包括但不仅限于音频替换、杂谈、排行榜等内容"))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("番剧（主分区）", "13", ""))
        list.add(ClassSmartTable("连载动画", "33", "当季连载的动画番剧"))
        list.add(ClassSmartTable("完结动画", "32", "已完结的动画番剧合集"))
        list.add(ClassSmartTable("资讯", "51", "动画番剧相关资讯视频"))
        list.add(ClassSmartTable("官方延伸", "152", "动画番剧为主题的宣传节目、采访视频，及声优相关视频"))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("国创（主分区）", "167", ""))
        list.add(ClassSmartTable("国产动画", "153", "我国出品的PGC动画"))
        list.add(ClassSmartTable("国产原创相关", "168", ""))
        list.add(ClassSmartTable("布袋戏", "169", ""))
        list.add(ClassSmartTable("动态漫·广播剧", "195", ""))
        list.add(ClassSmartTable("资讯", "170", ""))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("音乐（主分区）", "3", ""))
        list.add(ClassSmartTable("原创音乐", "28", "个人或团队制作以音乐为主要原创因素的歌曲或纯音乐"))
        list.add(ClassSmartTable("翻唱", "31", "一切非官方的人声再演绎歌曲作品"))
        list.add(
            ClassSmartTable(
                "VOCALOID·UTAU",
                "30",
                "以雅马哈Vocaloid和UTAU引擎为基础，包含其他调教引擎，运用各类音源进行的歌曲创作内容"
            )
        )
        list.add(ClassSmartTable("电音", "194", "以电子合成器、音乐软体等产生的电子声响制作的音乐"))
        list.add(ClassSmartTable("演奏", "59", "传统或非传统乐器及器材的演奏作品"))
        list.add(ClassSmartTable("MV", "193", "音乐录影带，为搭配音乐而拍摄的短片"))
        list.add(ClassSmartTable("音乐现场", "29", "音乐实况表演视频"))
        list.add(ClassSmartTable("音乐综合", "130", "收录无法定义到其他音乐子分区的音乐视频"))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("舞蹈（主分区）", "129", ""))
        list.add(ClassSmartTable("宅舞", "20", "与ACG相关的翻跳、原创舞蹈"))
        list.add(ClassSmartTable("街舞", "198", "收录街舞相关内容，包括赛事现场、舞室作品、个人翻跳、FREESTYLE等"))
        list.add(ClassSmartTable("明星舞蹈", "199", "国内外明星发布的官方舞蹈及其翻跳内容"))
        list.add(ClassSmartTable("中国舞", "200", "传承中国艺术文化的舞蹈内容，包括古典舞、民族民间舞、汉唐舞、古风舞等"))
        list.add(ClassSmartTable("舞蹈综合", "154", "收录无法定义到其他舞蹈子分区的舞蹈视频"))
        list.add(ClassSmartTable("舞蹈教程", "156", "镜面慢速，动作分解，基础教程等具有教学意义的舞蹈视频"))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("游戏（主分区）", "4", ""))
        list.add(
            ClassSmartTable(
                "单机游戏",
                "17",
                "以所有平台（PC、主机、移动端）的单机或联机游戏为主的视频内容，包括游戏预告、CG、实况解说及相关的评测、杂谈与视频剪辑等"
            )
        )
        list.add(ClassSmartTable("电子竞技", "171", "具有高对抗性的电子竞技游戏项目，其相关的赛事、实况、攻略、解说、短剧等视频"))
        list.add(ClassSmartTable("手机游戏", "172", "以手机及平板设备为主要平台的游戏，其相关的实况、攻略、解说、短剧、演示等视频"))
        list.add(ClassSmartTable("网络游戏", "65", "由网络运营商运营的多人在线游戏，以及电子竞技的相关游戏内容。包括赛事、攻略、实况、解说等相关视频"))
        list.add(ClassSmartTable("桌游棋牌", "173", "桌游、棋牌、卡牌对战等及其相关电子版游戏的实况、攻略、解说、演示等视频"))
        list.add(ClassSmartTable("GMV", "121", "由游戏素材制作的MV视频。以游戏内容或CG为主制作的，具有一定创作程度的MV类型的视频"))
        list.add(ClassSmartTable("音游", "136", "各个平台上，通过配合音乐与节奏而进行的音乐类游戏视频"))
        list.add(ClassSmartTable("Mugen", "19", "以Mugen引擎为平台制作、或与Mugen相关的游戏视频"))
        list.add(ClassSmartTable("-", "-", "-"))
        list.add(ClassSmartTable("知识（主分区）", "36", ""))
        list.add(ClassSmartTable("数码（主分区）", "188", ""))
        list.add(ClassSmartTable("生活（主分区）", "160", ""))
        list.add(ClassSmartTable("美食（主分区）", "211", ""))
        list.add(ClassSmartTable("鬼畜（主分区）", "119", ""))
        list.add(ClassSmartTable("时尚（主分区）", "155", ""))
        list.add(ClassSmartTable("资讯（主分区）", "202", ""))
        list.add(ClassSmartTable("娱乐（主分区）", "5", ""))
        list.add(ClassSmartTable("影视（主分区）", "181", ""))
        list.add(ClassSmartTable("纪录片（主分区）", "177", ""))
        list.add(ClassSmartTable("电影（主分区）", "23", ""))
        list.add(ClassSmartTable("电视剧（主分区）", "11", ""))

        bilibili_settings_help_smarttable.setData(list)
        bilibili_settings_help_smarttable.config.contentStyle =
            FontStyle(50, ContextCompat.getColor(this, R.color.black))
        bilibili_settings_help_smarttable.config.tableTitleStyle=FontStyle(50, ContextCompat.getColor(this, R.color.black))
        bilibili_settings_help_smarttable.config.xSequenceStyle=FontStyle(50, ContextCompat.getColor(this, R.color.black))
        bilibili_settings_help_smarttable.config.ySequenceStyle=FontStyle(50, ContextCompat.getColor(this, R.color.black))
        bilibili_settings_help_smarttable.config.columnTitleStyle=FontStyle(50, ContextCompat.getColor(this, R.color.black))


    }
}