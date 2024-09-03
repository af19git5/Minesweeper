package com.jimmyworks.minesweeper.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/** sqlite檔案名稱 */
actual val DATABASE_NAME = "minesweeper.db"

/**
 * 取得Room DatabaseBuilder
 *
 * @param context Context
 * @return Room DatabaseBuilder
 * @author Jimmy Kang
 */
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
        factory = AppDatabaseConstructor::initialize
    )
}
