package com.xayah.acmezone.Class

import com.bin.david.form.annotation.SmartColumn
import com.bin.david.form.annotation.SmartTable

@SmartTable(name = "Blibili分区一览")
class ClassSmartTable(
    @field:SmartColumn(id = 0, name = "名称")
    private val name: String,
    @field:SmartColumn(id = 1, name = "tID")
    private val tID: String,
    @field:SmartColumn(id = 2, name = "简介")
    private val desc: String,
)