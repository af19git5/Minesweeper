package com.jimmyworks.minesweeper.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.jimmyworks.minesweeper.database.dao.SettingDAO
import com.jimmyworks.minesweeper.database.entity.SettingEntity

/** sqlite檔案名稱 */
internal const val DATABASE_NAME = "minesweeper.db"

/** App用資料庫 */
@Database(
    version = 1,
    entities = [
        SettingEntity::class
    ]
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun settingDAO(): SettingDAO
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
