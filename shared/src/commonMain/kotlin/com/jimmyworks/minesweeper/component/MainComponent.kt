package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.jimmyworks.minesweeper.database.AppDatabase
import com.jimmyworks.minesweeper.database.entity.SettingEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * 主畫面Component
 *
 * @author Jimmy Kang
 */
class MainComponent(
    componentContext: ComponentContext,
    private val appDatabase: AppDatabase,
    private val onGameStart: (Int, Int, Int) -> Unit
) : BasicComponent(componentContext) {

    val settingFlow = appDatabase.settingDAO().findFirst()

    fun gameStart(x: Int, y: Int, minesCount: Int) {
        this.onGameStart(x, y, minesCount)
    }

    suspend fun saveSetting(setting: SettingEntity) = coroutineScope {
        launch { appDatabase.settingDAO().insert(setting) }
    }
}
