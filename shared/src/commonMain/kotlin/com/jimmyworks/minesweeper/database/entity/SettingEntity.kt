package com.jimmyworks.minesweeper.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 基礎設定
 *
 * @author Jimmy Kang
 */
@Entity("setting")
data class SettingEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("setting_id")
    val settingId: Long = 0,

    @ColumnInfo("x")
    var x: Int = 9,

    @ColumnInfo("y")
    var y: Int = 9,

    @ColumnInfo("mines_count")
    var minesCount: Int = 10
)
