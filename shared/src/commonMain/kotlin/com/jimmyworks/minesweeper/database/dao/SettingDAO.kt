package com.jimmyworks.minesweeper.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jimmyworks.minesweeper.database.entity.SettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SettingEntity): Long

    @Query("SELECT count(*) FROM setting")
    suspend fun count(): Int

    @Query("SELECT * FROM setting ORDER BY setting_id ASC LIMIT 1")
    fun findFirst(): Flow<SettingEntity>
}
